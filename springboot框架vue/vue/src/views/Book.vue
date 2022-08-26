<template>
  <div style="padding: 10px">
    <!--    </功能区域>-->

    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" >新增</el-button>
      <el-popconfirm title="确定删除么" @confirm="deleteBatch">
        <template #reference>
          <el-button type="danger" >批量删除</el-button>
        </template>
      </el-popconfirm>

    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">

      <el-input v-model="search" placeholder="Please input" style="width: 30%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="price" label="单价"></el-table-column>
      <el-table-column prop="author" label="作者"></el-table-column>
      <el-table-column prop="createTime" label="出版时间"></el-table-column>
      <el-table-column
          label="出版时间">
<!--        显示图片-->
        <template #default="scope">
          <el-imagex
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
          ></el-imagex>
        </template>

      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
<!--          v-if="user.role   === 1"-->
          <el-button  size="mini" target="_blank" v-if="user.role === 1" >购买 </el-button>
          <el-button  size="mini" target="_blank" @click="handleEdit(scope.row)" v-if="user.role === 1" >编辑 </el-button>

          <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)" >
            <template #reference>
              <el-button size="mini" target="_blank" v-if="user.role === 1" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px">
      <div class="demonstration">All combined</div>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      />
      <el-dialog v-model="dialogTableVisible" title="Shipping address">
        <el-table :data="gridData">
          <el-table-column property="date" label="Date" width="150"/>
          <el-table-column property="name" label="Name" width="200"/>
          <el-table-column property="address" label="Address"/>
        </el-table>
      </el-dialog>

      <!-- Form -->
      <el-button text @click="()=>{this.dialogFormVisible = true}">open a Form nested Dialog</el-button>

      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"
          :before-close="handleClose"
      >
        <el-form :model="form" label-width="120px">
          <el-form-item label="名称">
            <el-input v-model="form.name"/>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="form.price"/>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author"/>
          </el-form-item>
          <el-form-item label="出版时间">
          <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width: 60%" clearable></el-date-picker>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload  action="http://localhost:9092/files/upload" :on-success="filesUploadSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <template #footer>
          <div>
            <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
              <!--              这里不能保存，要修改！！！你调用了保存接口！！！所以主键冲突了-->

            <el-button type="primary" @click="save">确认</el-button>
          </span>
          </div>
        </template>
      </el-dialog>
    </div>
    <!-- 为什么我的按钮出不来-->
  </div>
</template>

<script>
// @ is an alias to /src
//1 插入1部分
// const handleEdit = () => {
//   console.log('click')
// }

import request from "@/utils/request";

export default {
  name: 'Book',
  components: {},
  data() {
    return {
      user: {},
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      dialogTableVisible: false,
      ids:[]
    }
  },

  created() {
    let userStr= sessionStorage.getItem("user")||"{}"
    this.user =JSON.parse(userStr)
    this.load()
  },
  methods: {
    handleSelectionChange(val){
      this.ids=val.map(v => v.id)
    },
      deleteBatch(){
      if(!this.ids.length){
        this.$message.warning("请选择数据")
        return
      }
      request.post("/book/deleteBatch", this.ids).then(res => {
        if(res.code ==='0'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error(res.msg)
        }

      })
    },
    filesUploadSuccess(res){
      console.log(res)
      this.form.cover=res.data
    },
    handleClose() {

    },
    load() {
      request.get("/book", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {}

    },
    save() {
      if(this.form.id){ //更新
        request.put("/book", this.form).then(res => {
          console.log(res)
          this.load() //刷新
          this.dialogVisible=false //关闭弹窗
        })

      } else{  //新增
        request.post("/book", this.form).then(res => {
          console.log(res)

        })
      }



    },
    // 修改2部分
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/book/" + id)
      this.load()

    },
    handleSizeChange(pageSize) { //改变当前个数触发
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum) { //改变页数触发
      this.currentPage=pageNum
      this.load()
    },
    currentPageChange() {
    }
  }
}
</script>
