<template>
<div style="width: 100%;height: 100vh;background-color: rgba(9,16,128,0.44);overflow: hidden" >
 <div style="width: 400px;margin: 150px auto">
   <div style="color: #cccccc;font-size: 35px; text-align: center">欢迎登录</div>
   <el-form ref="form" :model="form" size="normal">
   <el-form-item>
   <el-input  v-model="form.username"></el-input>

   </el-form-item>
     <el-form-item>
       <el-input v-model="form.password " show-password></el-input>
     </el-form-item>
     <el-form-item>
       <div style="display:flex">
         <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width:50%;" placeholder="请输入验证码"></el-input>
         <ValidCode @input="createValidCode" />
       </div>
     </el-form-item>
     <el-form-item>
       <el-radio v-model="form.role" :label="1">管理员</el-radio>
       <el-radio v-model="form.role" :label="2">普通用户</el-radio>
     </el-form-item>
     <el-form-item>
       <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
     </el-form-item>
     </el-form>
 </div>

</div>
</template>

<script>
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";

export default {
  name: "login",
  components:{
   ValidCode
  },
  data(){
   return{
     form:{},
     validCode:''
   }

  },
  methods:{
    createValidCode(data){
      this.validCode =data

    },
    login(){
      this.$refs['form'].validate((valid)=>{
        if(valid){
          if(!this.form.validCode){
              this.$message.error("请填写验证码")
            return
          }
          if(this.form.validCode.toLowerCase()!==this.validCode.toLowerCase()){
            this.$message.error("验证码错误")
            return;
          }
          request.post("user/login",this.form).then(res=> {
            if (res.code === '0') {
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              this.$message({
                type:"success",
                message:"登录成功"
              })

              this.$router.push("/")
            } else {

            }
          })
        }


          }
      )

    }
  }
}
</script>

<style scoped>

</style>