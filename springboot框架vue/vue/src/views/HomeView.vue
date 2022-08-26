<template >
  <div style="padding: 10px">
    <!--    </功能区域>-->

    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">

      <el-input v-model="search" placeholder="Please input" style="width: 30%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="nickName" label="昵称"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="角色">
                     <template #default="scope">
                       <span v-if="scope.row.role ===1">管理员</span>
                       <span v-if="scope.row.role ===2">普通用户</span>
                     </template>
                   </el-table-column>


        <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button size="mini" type="success" target="_blank" @click="showBooks(scope.row.bookList)" >查看图书列表 </el-button>
          <el-button size="mini" target="_blank" @click="handleEdit(scope.row)" >编辑  </el-button>
          <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button target="_blank"  >删除</el-button>
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

      <el-dialog title="用户拥有的图书列表" v-model="bookVis" width="30%">
       <el-table :data="bookList" stripe border>
        <el-table-column prop="id" label="ID"></el-table-column>
         <el-table-column prop="name" label="名称"></el-table-column>
         <el-table-column prop="price" label="价格"></el-table-column>
       </el-table>
      </el-dialog>
      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"
          :before-close="handleClose"
      >

        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.username"/>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickName"/>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age"/>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.sex">
              <el-radio label="1" size="large">男</el-radio>
              <el-radio label="2" size="large">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="form.address"/>
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
  name: 'HomeView',
  components: {},
  data() {
    return {
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      dialogTableVisible: false,
      bookList:[],
      bookVis:false
    }
  },

  created() {
    this.load()
  },
  methods: {
    showBooks(books){
      this.bookList=books
      this.bookVis =true
    },
    handleClose() {

    },
    load() {
      request.get("/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        //将后台数据传给tableData
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
        request.put("/user", this.form).then(res => {
          console.log(res)
         this.load() //刷新
          this.dialogVisible=false //关闭弹窗
        })

      } else{  //新增
        request.post("/user", this.form).then(res => {
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
      request.delete("/user/" + id)
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
