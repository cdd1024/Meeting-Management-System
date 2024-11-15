<template>
  <div>
    <div class="login-container">
      <div style="width: 350px" class="login-box">
        <div style="font-weight: bold; font-size: 24px;text-align: center;margin-bottom: 30px">注册</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item prop="company">
            <el-input  prefix-icon="OfficeBuilding" v-model="data.form.company" placeholder="请输入公司名称"/>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input  prefix-icon="PhoneFilled" v-model="data.form.phone" placeholder="请输入联系方式"/>
          </el-form-item>
          <el-form-item prop="username">
            <el-input  prefix-icon="User" v-model="data.form.username" placeholder="请输入账号"/>
          </el-form-item>
          <el-form-item prop="password">
            <el-input   show-password prefix-icon="Lock" v-model="data.form.password" placeholder="请输入密码"/>
          </el-form-item>
            <el-form-item label="验证码">
              <el-input placeholder="请输入验证码（字母为小写）" v-model="sidentifyMode" clearable></el-input>
            </el-form-item>
          <el-form-item>
            <!-- 使用验证码组件 -->
            <div class="code" @click="refreshCode">
              <SIdentify :identifyCode="identifyCode"></SIdentify>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="register">注册</el-button>
          </el-form-item>
        </el-form>
        <div style="margin-top: 30px;text-align: right">
          已有账号？请<a href="/login">登录</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {Message} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import router from "../router";
import request from "../utils/request";

import SIdentify from '../views/components/Sidentify.vue'
import { reactive,ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

let $router = useRouter()
let sidentifyMode = ref('') //输入框验证码
let identifyCode = ref('') //图形验证码
let identifyCodes = ref('1234567890abcdefjhijklinopqrsduvwxyz') //验证码出现的数字和字母
const data=reactive({
  form:{}
})

const rules=reactive({
  company: [
    { required: true, message: '请输入公司名称', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '请输入联系方式', trigger: 'blur' },
  ],
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password:[
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],

  })

const formRef=ref()

const register=()=>{
  formRef.value.validate((valid)=>{

    if(!sidentifyMode.value){
      ElMessage({ type: 'error', message: '请输入验证码' })
    }
    else if (sidentifyMode.value != identifyCode.value) {
      ElMessage({ type: 'error', message: '验证码错误' })
      refreshCode()
      return
    } else if(valid) {
        request.post('/register', data.form).then(res => {
          if (res.code == '200') {
            ElMessage.success('注册成功')
            router.push('/login') //跳转到主页，且不会刷新
          } else {
            ElMessage.error(res.msg)
          }
        })
      }
  })
}
//组件挂载
onMounted(() => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
})

// 生成随机数
const randomNum = (min, max) => {
  max = max + 1
  return Math.floor(Math.random() * (max - min) + min)
}
// 随机生成验证码字符串
const makeCode = (o, l) => {
  for (let i = 0; i < l; i++) {
    identifyCode.value += o[randomNum(0, o.length)]
  }
}
// 更新验证码
const refreshCode = () => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
}

</script>

<style scoped>
.login-container{
  min-height: 100vh;overflow: hidden;display: flex;align-items: center;justify-content: center;
  background-image: url("../assets/imgs/avatar3.jpg");
  background-size: cover;
}
.login-box{
  background-color: rgba(255,255,255,.8);
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  padding: 30px;
  border-radius: 5px;
}
</style>