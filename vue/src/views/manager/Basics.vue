<template>
  <div>
    <div class="card" style="width: 50%;padding: 40px ">
      <el-form :model="data.form" ref="formRef" :rules="rules" label-width="100px" label-position="right" style="padding-right:40px " >
        <el-form-item label="头像" >
          <el-upload class="avatar-uploader" action="http://localhost:9090/files/upload" :show-file-list="false"
                     :on-success="handelImgUploadSuccess">
            <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="账号" >
          <el-input v-model="data.form.username" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="data.form.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="data.form.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="data.form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "../../utils/request";
import {ElMessage} from "element-plus";
import router from "../../router";

const data=reactive({
  form:JSON.parse(localStorage.getItem('enterprise-user')||"{}")
})

const handelImgUploadSuccess=(res)=>{
  data.form.avatar=res.data
}

/**
 * <!--request.put({
 * {
 *   url: data.form.role === "ENTERPRISE" ? '/enterprise/update' : '/administrator/update',
 *     data:data.form
 * }
 * })-->
 */

const update=()=>{

  formRef.value.validate((valid)=>{
    if(valid){

      request.request(
          {
            url: data.form.role === "ENTERPRISE" ? '/enterprise/update' : '/administrator/update',
            method:'PUT',
            data:data.form
          }
      ).then(res=>{
        if(res.code=='200'){
          ElMessage.success("操作成功")
          router.push('/login')//更新后重新登录一下
        }else{
          ElMessage.error(res.msg)
        }
      })
    }

  })

}


const formRef=ref()

const rules=reactive({
  name:[
    { required: true, message: '请输入名称', trigger: 'blur' },
  ],
  phone:[
    { required: true, message: '请输入联系方式', trigger: 'blur' },
  ],
  email:[
    { required: true, message: '请输入邮箱', trigger: 'blur' },
  ],
  sex:[
    { required: true,message: '请选择性别',trigger: 'blur' },
  ],
})
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  text-align: center;
}
.avatar{
  width: 100px;
  height: 100px;
  display: block;
}
</style>