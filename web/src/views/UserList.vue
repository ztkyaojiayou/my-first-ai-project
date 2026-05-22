<template>
  <div class="user-list">
    <a-card title="用户管理">
      <!-- 表格 -->
      <a-table
        :columns="columns"
        :data-source="userList"
        :loading="loading"
        :pagination="pagination"
        row-key="id"
        @change="handleTableChange"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '正常' : '禁用' }}
            </a-tag>
          </template>
          <template v-else-if="column.key === 'action'">
            <a-space>
              <a-button size="small" @click="handleEdit(record)">编辑</a-button>
              <a-popconfirm
                title="确定要删除这个用户吗？"
                @confirm="handleDelete(record.id)"
              >
                <a-button size="small" danger>删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script setup lang="ts">
/**
 * 用户管理页面
 * 功能：用户列表查看、编辑、删除（管理员权限）
 * 对接后端：UserController.getList(), update(), delete()
 */
import { ref, onMounted } from 'vue'
import { userApi } from '@/api'
import { message } from 'ant-design-vue'

const loading = ref(false)
const userList = ref<any[]>([])
const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0
})

// 表格列定义
const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '用户名', dataIndex: 'username', key: 'username' },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname' },
  { title: '邮箱', dataIndex: 'email', key: 'email' },
  { title: '手机号', dataIndex: 'phone', key: 'phone' },
  { title: '状态', key: 'status' },
  { title: '操作', key: 'action', width: 180 }
]

// 获取用户列表
async function fetchUserList() {
  loading.value = true
  try {
    const res = await userApi.getList({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize
    })
    userList.value = res.records || []
    pagination.value.total = res.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 分页变化
function handleTableChange(pag: any) {
  pagination.value.current = pag.current
  pagination.value.pageSize = pag.pageSize
  fetchUserList()
}

// 编辑用户
function handleEdit(record: any) {
  message.info('编辑功能开发中...')
}

// 删除用户
async function handleDelete(id: number) {
  try {
    await userApi.delete(id)
    message.success('删除成功')
    fetchUserList()
  } catch (error) {
    console.error(error)
  }
}

// 页面加载
onMounted(() => {
  fetchUserList()
})
</script>

<style scoped>
.user-list {
  padding: 0;
}
</style>
