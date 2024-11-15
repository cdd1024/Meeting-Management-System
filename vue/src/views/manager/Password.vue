<template>
  <div>
    <div class="card" style="width: 50%;padding: 40px ">
      <el-form :model="ruleForm" ref="ruleFormRef" :rules="rules" label-width="100px" label-position="right" style="padding-right:40px ">
      <el-form-item label="原始密码" prop="oldPassword" >
        <el-input v-model="ruleForm.oldPassword" placeholder="请输入原始密码" autocomplete="off" />
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input show-password v-model="ruleForm.newPassword" placeholder="请设置6-20位字母，数字的两种组合" autocomplete="off" />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input show-password v-model="ruleForm.confirmPassword" placeholder="请再次确认新密码" autocomplete="off" />
      </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="updatePass">确认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "../../utils/request";
import router from "../../router";

const data=reactive({
  form:JSON.parse(localStorage.getItem('enterprise-user')||"{}"),
})

const ruleForm = reactive({
  oldPassword:'',//原始密码
  newPassword: '',//新密码
  confirmPassword: ''//确认新密码
})



const ruleFormRef=ref()



const validateP=(rule, value, callback)=>{
  if(value === ''){
    callback(new Error('请输入原始密码！'))
  }else if(value !== data.form.password){
    callback(new Error('原始密码错误！'))
  }else{}
  ruleFormRef.value.validateField('newPassword', () => null)
callback()
}
//检查新密码格式
const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('密码不能为空！'))
  } else {
    let str = RegExp("^[0-9a-zA-Z]*$")
    if (!str.test(value) || value.length < 6 || value.length > 20) {
      callback(new Error('当前密码格式不正确！'))
    } else{
      ruleFormRef.value.validateField('confirmPassword', () => null)
      callback()
  }
  }
}
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('密码不能为空！'))

  } else if (value !== ruleForm.newPassword) {
    callback(new Error('两次输入密码不一致！'))
  }else{
    callback()
  }
}
const rules = reactive({
  oldPassword: [{ validator: validateP, trigger: 'blur' }],
  newPassword: [{ validator: validatePass, trigger: 'blur' }],
  confirmPassword: [{ validator: validatePass2, trigger: 'blur' }],
})

const updatePass=()=>{
  ruleFormRef.value.validate((valid)=>{
    if(valid){
      data.form.password=ruleForm.newPassword
      request.request(
          {
            url: data.form.role === "ENTERPRISE" ? '/enterprise/update' : '/administrator/update',
            method:'PUT',
            data:data.form
          }
      ).then(res=>{
        if(res.code=='200'){
          ElMessage.success("操作成功")
          router.push('/login')
        }else{
          ElMessage.error(res.msg)
        }
      })
    }})
}



</script>