<template>
  <div id="app">
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 260px;margin-right: 10px" v-model="data.name" placeholder="请输入会议名称查询"
                :prefix-icon="Search"/>
      <el-input style="width: 260px;margin-right: 10px" v-model="data.company" placeholder="请输入创建人查询"/>
      <el-date-picker style="width: 260px;margin-right: 10px" format="YYYY-MM-DD" value-format="YYYY-MM-DD"
                      v-model="data.example" placeholder="请选择会议开始的日期"></el-date-picker>
      <el-button color="#00C5CD" style="margin-left:10px" @click="load" plain>查询</el-button>
      <el-button type="info" @click="reset" plain>重置</el-button>
      <el-button type="warning" @click="handelAdd" plain>新增</el-button>
      <el-button  type="success" @click="exportBtn" plain>导出</el-button>
    </div>


    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table :data="data.tableData" style="width: 100%" ref="exportTableRef"
                  :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }">
          <el-table-column prop="name" label="会议名称"/>
          <el-table-column prop="company" label="创建人"/>
          <el-table-column prop="statu" label="会议状态"/>
          <el-table-column prop="example" label="开始时间">
            <template #default="{ row }">
              {{ formatDate(row.example) }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button type="primary" size="small" plain @click="handelEdit(scope.row)" v-if="scope.row.company==user.company">编辑</el-button>
              <el-button type="danger" size="small" plain @click="del(scope.row.id)" v-if="scope.row.company==user.company"> 删除</el-button>
            </template>
          </el-table-column>
        </el-table>


      </div>
    </div>


    <div class="card">
      <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     @current-change="handelCurrentChange"
                     background layout="prev, pager, next" :total="data.total"/>
    </div>

    <el-dialog width="65%" v-model="data.formVisible" title="会议信息" >
      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right:40px " ref="formRef" :rules="rules">
        <el-form-item label="会议封面" prop="avatar">
          <el-upload class="avatar-uploader" action="http://localhost:9090/files/upload" :show-file-list="false"
                     :on-success="handelImgUploadSuccess">
            <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="会议名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="会议内容" prop="descr">
          <BasicEditor v-if="data.formVisible" v-model="data.form.descr" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="创建人 "  >
          <el-input v-model="user.company" autocomplete="off" disabled/>
        </el-form-item>

        <el-form-item label="会议状态">
          <el-select style="width: 20%" v-model="data.form.statu">
            <el-option value="已结束" label="已结束"></el-option>
            <el-option value="进行中" label="进行中"></el-option>
            <el-option value="未开始" label="未开始"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间"  prop="example">
          <el-date-picker
              v-model="data.form.example"
              type="datetime"
              placeholder="请选择会议开始时间"
              autocomplete="off"
              value-format="YYYY-MM-DD HH:mm:ss"
              :rules="[{ required: true, message: '请选择会议开始时间', trigger: 'change' }]"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="examplea">
              <el-date-picker
                  v-model="data.form.examplea"
                  type="datetime"
                  placeholder="请选择会议结束时间"
                  autocomplete="off"
                  value-format="YYYY-MM-DD HH:mm:ss"
                  :rules="[{ required: true, message: '请选择会议结束时间', trigger: 'change' }]"
              />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible=false">取消</el-button>
          <el-button type="primary" @click="save" plain>
            保存
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>

</template>

<script  setup>
import {reactive,ref} from "vue";
import {Search} from '@element-plus/icons-vue'
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
import BasicEditor from '../components/Basic.vue';

import * as XLSX from 'xlsx';

const formRef=ref()

const rules=reactive({
  name:[
    { required: true, message: '请输入会议名称', trigger: 'blur' },
  ],

  descr:[
    { required: true, message: '请输入会议内容', trigger: 'blur' },
  ],
  example:[
    { type: 'string',required: true,message: '请选择开始时间',trigger: 'blur' },
  ],
  examplea:[
    {type: 'string', required: true,message: '请选择结束时间',trigger: 'blur' },
  ],
  avatar:[
    {type: 'string', required: true,message: '请选择头像',trigger: 'blur' },
  ],
})

const exportTableRef = ref(null);

const exportBtn = () => {
  request.get('conference/selectById',{
  }).then(res=>{
    data.allData=res.data;
    if(!data.allData) return;
    console.log(data.allData.values())
    const tableHeaders = ['ID', 'Name', 'Company','Begin','End'];
    const myDataArray =[tableHeaders].concat( data.allData.map(obj => [
      obj.id,
      obj.name,
      obj.company,
      obj.example,
      obj.examplea,
    ]));
    const ws = XLSX.utils.aoa_to_sheet(myDataArray);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, '公司会议数据.xlsx');
  })
}



const data = reactive({
  name: '',
  company: '',
  example: '',
  tableData: [],
  total: 0,
  pageSize: 5,//每页个数
  pageNum: 1,//当前页码
  formVisible: false,
  form: {},
  xlsx:[]
})

const load = () => {
  request.get('conference/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      company: data.company,
      example: data.example,
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

//调用方法获取后台数据
load()

const handelCurrentChange = (pageNum) => {
  //等翻页的时候重新加载数据即可
  load()
}
const user = JSON.parse(localStorage.getItem('enterprise-user') || '{}')

const reset = () => {
  data.name = ''
  data.company = ''
  data.example = ''
  load()
}

const formatDate = (datetime) => {
  return new Date(datetime).toLocaleString(); // 示例：使用new Date(datetime).toLocaleString() JavaScript 内置函数来格式化
}

const del = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？', '删除确认', {
    type: 'warning'
  }).then(res => {
    request.delete('/conference/delete/' + id).then(res => {
      if (res.code == '200') {
        load()
        ElMessage.success("操作成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(res => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })
}


const handelAdd=()=>{

  data.form={}
  data.formVisible=true
}
const handelImgUploadSuccess=(res)=>{
  data.form.avatar=res.data

}

const save=()=>{
  formRef.value.validate((valid)=>{
    if(valid) {
      data.form.company=user.company
      request.request({
        url: data.form.id ? '/conference/update' : '/conference/add',
        method: data.form.id ? 'PUT' : 'POST',
        data: data.form
      }).then(res => {
        if (res.code == '200') {
          load()
          data.formVisible = false
          ElMessage.success("操作成功")
        } else {
          ElMessage.error(res.msg)
        }
      })

    }})
}

const handelEdit=(row)=>{
  data.form=JSON.parse(JSON.stringify(row))
  data.formVisible=true
}


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