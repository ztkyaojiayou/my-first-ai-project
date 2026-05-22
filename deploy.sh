#!/bin/bash
#==========================================
# 用户管理系统 - 部署脚本
# 功能：使用 docker-compose 部署服务
#==========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 项目配置
COMPOSE_FILE="docker-compose.yml"

# 打印信息
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 显示用法
usage() {
    echo "用法: $0 [命令]"
    echo ""
    echo "命令:"
    echo "  start     启动所有服务"
    echo "  stop      停止所有服务"
    echo "  restart   重启所有服务"
    echo "  logs      查看服务日志"
    echo "  status    查看服务状态"
    echo "  rebuild   重新构建并启动"
    echo "  clean     清理所有容器和镜像"
    echo "  health    检查服务健康状态"
}

# 检查依赖
check_dependencies() {
    log_info "检查依赖..."

    if ! command -v docker &> /dev/null; then
        log_error "Docker 未安装"
        exit 1
    fi

    if [ ! -f "${COMPOSE_FILE}" ]; then
        log_error "docker-compose.yml 文件不存在"
        exit 1
    fi

    log_info "依赖检查完成"
}

# 启动服务
start_services() {
    log_info "启动服务..."
    docker-compose up -d
    log_info "服务启动完成"
}

# 停止服务
stop_services() {
    log_info "停止服务..."
    docker-compose down
    log_info "服务停止完成"
}

# 查看日志
show_logs() {
    echo "按 Ctrl+C 退出日志查看"
    docker-compose logs -f
}

# 查看状态
show_status() {
    docker-compose ps
}

# 健康检查
health_check() {
    log_info "执行健康检查..."

    # 检查后端
    if curl -f -s http://localhost:8080/api/health/check > /dev/null 2>&1; then
        log_info "✓ 后端服务健康"
    else
        log_error "✗ 后端服务异常"
    fi

    # 检查前端
    if curl -f -s http://localhost:80 > /dev/null 2>&1; then
        log_info "✓ 前端服务健康"
    else
        log_error "✗ 前端服务异常"
    fi
}

# 重新构建
rebuild_services() {
    log_info "重新构建镜像..."
    docker-compose build --no-cache
    log_info "重新启动服务..."
    docker-compose up -d
    log_info "重建完成"
}

# 清理
clean_services() {
    log_warn "即将清理所有容器和镜像..."
    read -p "确认删除? (y/n): " confirm
    if [ "$confirm" == "y" ]; then
        log_info "清理容器..."
        docker-compose down
        log_info "清理未使用的镜像..."
        docker image prune -f
        log_info "清理完成"
    else
        log_info "取消清理"
    fi
}

# 主函数
main() {
    echo "========================================"
    echo "  用户管理系统 - 部署脚本"
    echo "========================================"
    echo ""

    if [ $# -eq 0 ]; then
        usage
        exit 0
    fi

    check_dependencies

    case "$1" in
        start)
            start_services
            ;;
        stop)
            stop_services
            ;;
        restart)
            stop_services
            start_services
            ;;
        logs)
            show_logs
            ;;
        status)
            show_status
            ;;
        health)
            health_check
            ;;
        rebuild)
            rebuild_services
            ;;
        clean)
            clean_services
            ;;
        -h|--help)
            usage
            ;;
        *)
            log_error "未知命令: $1"
            usage
            exit 1
            ;;
    esac
}

main "$@"
