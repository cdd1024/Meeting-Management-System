
  <template>
    <div>
      <div class="card" style="width: 50%;padding: 40px ">
        <el-form :model="data.form"  label-width="100px" label-position="right" style="padding-right:40px " >
          <el-form-item label="头像" >
            <img  :src="data.form.avatar||'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="" class="avatar" />
          </el-form-item>
          <el-form-item label="账号" disabled>
            <el-input v-model="data.form.username" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="名称" disabled>
            <el-input v-model="data.form.name" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="手机号" disabled>
            <el-input v-model="data.form.phone" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="邮箱" disabled>
            <el-input v-model="data.form.email" autocomplete="off" disabled/>
          </el-form-item>
          <el-form-item label="性别" >
            <el-radio-group v-model="data.form.sex" disabled>
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="公司" v-if="data.form.role==='ENTERPRISE'" >
            <el-input v-model="data.form.company" autocomplete="off" disabled />
          </el-form-item>
          <el-form-item label="身份">
            <el-select style="width: 100%" v-model="data.form.role" disabled>
              <el-option value="ADMINISTRATOR" label="超级管理员"></el-option>
              <el-option value="ENTERPRISE" label="企业管理员"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </template>

<script setup>
import {reactive} from "vue";

const data=reactive({
  form:JSON.parse(localStorage.getItem('enterprise-user')||"{}")
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