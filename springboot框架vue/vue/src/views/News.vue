<template>
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
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column prop="author" label="作者"></el-table-column>
      <el-table-column prop="time" label="出版时间"></el-table-column>

      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button target="_blank" @click="details(scope.row)" >详情 </el-button>

          <el-button target="_blank" @click="handleEdit(scope.row)" >编辑  </el-button>
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

    </div>
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
        <el-form-item label="标题">
          <el-input v-model="form.title"/>
        </el-form-item>
        <div id="div1"></div>
<!--        <el-form-item label="内容">-->
<!--          <el-input v-model="form.price"/>-->
<!--        </el-form-item>-->

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
    <el-dialog
        v-model="vis"
        title="详情"
        width="30%"
        :before-close="handleClose"
    >
      <el-card>
       <div v-html="datail.content">

       </div>

      </el-card>

    </el-dialog>>
    <!-- 为什么我的按钮出不来-->
  </div>
</template>

<script>
// @ is an alias to /src
//1 插入1部分
// const handleEdit = () => {
//   console.log('click')
// }
import E from 'wangeditor'
import request from "@/utils/request";
let editor;
export default {
  name: 'News',
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
      vis:false,
      dialogTableVisible: false,
      datail:{}

    }
  },

  created() {
    this.load()
  },

  methods: {
    details(row){
     this.datail=row
     this.vis= true
    },
    filesUploadSuccess(res){
      console.log(res)
      this.form.cover=res.data
    },
    handleClose() {

    },
    load() {
      request.get("/news", {
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
      this.$nextTick(() => {
         editor =new E('#div1')
        editor.create()

      })

    },
    save() {
      this.form.content= editor.txt.html()

      if(this.form.id){ //更新
        request.put("/news", this.form).then(res => {
          console.log(res)
          this.load() //刷新
          this.dialogVisible=false //关闭弹窗
        })

      } else{  //新增

        let userStr = sessionStorage.getItem("user")|| "{}";
        let user =JSON.parse(userStr)
        this.form.author = user.nickName
        request.post("/news", this.form).then(res => {
          console.log(res)

        })
      }



    },
    // 修改2部分
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        editor =new E('#div1')
        editor.create()
        editor.txt.html(row.content)

      })
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/news/" + id)
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
