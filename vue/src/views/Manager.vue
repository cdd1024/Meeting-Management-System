<template>
  <div>
    <div style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
      <div style="flex: 1">
        <div style="padding-left: 20px; display: flex; align-items: center">
          <img src="@/assets/imgs/cmh.jpg" alt="" style="width: 40px">
          <div style="font-weight: bold; font-size: 24px; margin-left: 5px">测盟汇</div>
        </div>
      </div>
      <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
        <el-button @click="toggleFullScreen" :icon="fullScreenIcon" size="small" round style="margin-right: 10px"></el-button>
        <!-- 设置主页右上角头像-->
        <img :src="user.avatar||'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="" style="width: 40px; height: 40px;border-radius: 50%">
        <span style="margin-left: 10px">{{user.name}}</span>
      </div>
    </div>

    <div style="display: flex">
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu
            router
            style="border: none"
            :default-active="$route.path"
            :default-openeds="['/home', '2']"
        >
          <el-menu-item index="/home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><ChatSquare /></el-icon>
              <span>会议管理</span>
            </template>
            <el-menu-item index="/conference">
              <el-icon><ChatRound /></el-icon>
              <span>会议信息</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
            <el-icon><User /></el-icon>
            <span>个人资料</span>
            </template>
          <el-menu-item index="/basics">
            <el-icon><Avatar /></el-icon>
            <span>基本资料修改</span>
          </el-menu-item>
          <el-menu-item index="/password">
            <el-icon><Lock /></el-icon>
            <span>密码修改</span>
          </el-menu-item>
          <el-menu-item index="/person">
            <el-icon><UserFilled /></el-icon>
            <span>个人信息</span>
          </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="login" @click="logout" >
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>

      <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
        <router-view />
      </div>
    </div>

  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
const $route = useRoute()
console.log($route.path)
const user=JSON.parse(localStorage.getItem('enterprise-user')||'{}')
import screenfull from "screenfull";
import {  ZoomOut, ZoomIn } from '@element-plus/icons-vue';
import {computed ,ref} from 'vue';

const isFullScreen = ref(false);
const toggleFullScreen = () => {
  if (screenfull.isEnabled) {
    screenfull.toggle();
    isFullScreen.value = !isFullScreen.value;
  }
};

const fullScreenIcon = computed(() => {
  return isFullScreen.value ? ZoomOut : ZoomIn;
});

const logout = () => {
  localStorage.removeItem('enterprise-user')
}
</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #dcede9 !important;
}
.el-menu-item:hover {
  color: #11A983;
}
:deep(th)  {
  color: #333;
}
</style>