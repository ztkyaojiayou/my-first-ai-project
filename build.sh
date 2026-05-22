#!/bin/bash
#==========================================
# 用户管理系统 - 打包脚本
# 功能：构建 Docker 镜像并推送到仓库
#==========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 项目配置
PROJECT_NAME="userdemo"
IMAGE_NAME="userdemo-backend"
REGISTRY=""  # 可配置镜像仓库地址，如 docker.io/username/
TAG=${1:-latest}

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
    echo "用法: $0 [TAG]"
    echo "示例: $0 v1.0.0"
    echo ""
    echo "参数说明:"
    echo "  TAG     镜像标签，默认为 latest"
}

# 检查依赖
check_dependencies() {
    log_info "检查依赖..."

    if ! command -v docker &> /dev/null; then
        log_error "Docker 未安装，请先安装 Docker"
        exit 1
    fi

    if ! command -v docker-compose &> /dev/null && ! command -v docker &> /dev/null; then
        log_error "docker-compose 未安装"
        exit 1
    fi

    log_info "依赖检查完成"
}

# 清理构建
clean_build() {
    log_info "清理旧构建..."
    docker rmi ${REGISTRY}${IMAGE_NAME}:${TAG} 2>/dev/null || true
    log_info "清理完成"
}

# 构建镜像
build_image() {
    log_info "开始构建 Docker 镜像: ${REGISTRY}${IMAGE_NAME}:${TAG}"

    docker build -t ${REGISTRY}${IMAGE_NAME}:${TAG} .

    if [ $? -eq 0 ]; then
        log_info "镜像构建成功: ${REGISTRY}${IMAGE_NAME}:${TAG}"
    else
        log_error "镜像构建失败"
        exit 1
    fi
}

# 推送镜像
push_image() {
    if [ -z "${REGISTRY}" ]; then
        log_warn "未配置镜像仓库地址，跳过推送"
        return
    fi

    log_info "推送镜像到仓库: ${REGISTRY}${IMAGE_NAME}:${TAG}"

    docker push ${REGISTRY}${IMAGE_NAME}:${TAG}

    if [ $? -eq 0 ]; then
        log_info "镜像推送成功"
    else
        log_error "镜像推送失败"
        exit 1
    fi
}

# 使用 docker-compose 构建
build_with_compose() {
    log_info "使用 docker-compose 构建..."

    docker-compose build

    if [ $? -eq 0 ]; then
        log_info "docker-compose 构建成功"
    else
        log_error "docker-compose 构建失败"
        exit 1
    fi
}

# 主函数
main() {
    echo "========================================"
    echo "  ${PROJECT_NAME} 打包脚本"
    echo "========================================"
    echo ""

    # 解析参数
    if [ "$1" == "-h" ] || [ "$1" == "--help" ]; then
        usage
        exit 0
    fi

    check_dependencies

    # 根据参数选择构建方式
    if [ "$1" == "compose" ]; then
        build_with_compose
    else
        clean_build
        build_image
        push_image
    fi

    echo ""
    log_info "========================================"
    log_info "  构建完成！"
    log_info "========================================"
    echo ""
    echo "镜像信息:"
    echo "  - 镜像名: ${REGISTRY}${IMAGE_NAME}:${TAG}"
    echo "  - 本地测试: docker run -p 8080:8080 ${REGISTRY}${IMAGE_NAME}:${TAG}"
    echo ""
}

main "$@"
