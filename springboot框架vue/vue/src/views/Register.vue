<template>
<div style="width: 100%;height: 100vh;background-color: rgba(25,0,211,0.54);overflow: hidden" >
 <div style="width: 400px;margin: 150px auto">
   <div style="color: #cccccc;font-size: 50px; text-align: center">欢迎注册</div>
   <el-form ref="form" :model="form" size="normal">
   <el-form-item>
   <el-input  v-model="form.username"></el-input>

   </el-form-item>
     <el-form-item>
       <el-input v-model="form.password " show-password></el-input>
     </el-form-item>
     <el-form-item>
       <el-input v-model="form.confirm" show-password></el-input>
     </el-form-item>
     <el-form-item>
       <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
     </el-form-item>
     </el-form>
 </div>

</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "login",
  data(){
   return{
     form:{}
   }
  },
  methods:{
    register(){
     if(this.form.password !==this.form.confirm){
       this.$message(
           {
             message: '两次密码不一致哦'
           }
       )
       return
     }
      request.post("user/register",this.form).then(res=> {
        if (res.code === '0') {


          this.$router.push("/login")
        } else {

        }
      })
    }
  }
}
</script>

<style scoped>

</style>