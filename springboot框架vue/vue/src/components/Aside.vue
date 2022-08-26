<template>
 <div>
   <el-row class="tac">
     <el-col :span="24">
       <h5 class="mb-2">0.0我就看看你</h5>
       <el-menu
           style="width: 100%"
           router
           default-active="2"
           class="el-menu-vertical-demo"

       >
<!--         <el-sub-menu index="1" >-->
<!--           <template #title>-->
<!--             <el-icon><location /></el-icon>-->
<!--             <span>用户管理</span>-->
<!--           </template>-->
<!--         </el-sub-menu>-->
         <el-menu-item index="home">
           <el-icon><setting /></el-icon>
           <span>用户管理</span>
         </el-menu-item>

         <el-menu-item index="book">
           <el-icon><setting /></el-icon>
           <span>书籍管理</span>
         </el-menu-item>
         <el-menu-item index="news">
           <el-icon><setting /></el-icon>
           <span>新闻管理</span>
         </el-menu-item>
         <el-menu-item index="communication">
           <el-icon><setting /></el-icon>
           <span>聊天管理</span>
         </el-menu-item>
         <el-menu-item index="health">
           <el-icon><setting /></el-icon>
           <span>健康管理</span>
         </el-menu-item>
       </el-menu>
     </el-col>
<!--     <el-col :span="12">-->
<!--       <h5 class="mb-2">Custom colors</h5>-->
<!--       <el-menu-->
<!--           active-text-color="#ffd04b"-->
<!--           background-color="#545c64"-->
<!--           class="el-menu-vertical-demo"-->
<!--           default-active="2"-->
<!--           text-color="#fff"-->
<!--           @open="handleOpen"-->
<!--           @close="handleClose"-->
<!--       >-->
<!--         <el-sub-menu index="1">-->
<!--           <template #title>-->
<!--             <el-icon><location /></el-icon>-->
<!--             <span>Navigator One</span>-->
<!--           </template>-->
<!--           <el-menu-item-group title="Group One">-->
<!--             <el-menu-item index="1-1">item one</el-menu-item>-->
<!--             <el-menu-item index="1-2">item one</el-menu-item>-->
<!--           </el-menu-item-group>-->
<!--           <el-menu-item-group title="Group Two">-->
<!--             <el-menu-item index="1-3">item three</el-menu-item>-->
<!--           </el-menu-item-group>-->
<!--           <el-sub-menu index="1-4">-->
<!--             <template #title>item four</template>-->
<!--             <el-menu-item index="1-4-1">item one</el-menu-item>-->
<!--           </el-sub-menu>-->
<!--         </el-sub-menu>-->
<!--         <el-menu-item index="2">-->
<!--           <el-icon><icon-menu /></el-icon>-->
<!--           <span>Navigator Two</span>-->
<!--         </el-menu-item>-->
<!--         <el-menu-item index="3" disabled>-->
<!--           <el-icon><document /></el-icon>-->
<!--           <span>Navigator Three</span>-->
<!--         </el-menu-item>-->
<!--         <el-menu-item index="4">-->
<!--           <el-icon><setting /></el-icon>-->
<!--           <span>Navigator Four</span>-->
<!--         </el-menu-item>-->
<!--       </el-menu>-->
<!--     </el-col>-->
   </el-row>
 </div>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      user: {},
      path: this.$router.path
    }
  },
  created() {
    let userStr= sessionStorage.getItem("user")||"{}"
   this.user =JSON.parse(userStr)
    //防止通过修改缓存来确认当前登录用户的合法信息
    // Request.get("/user/"+this.user.id).then(res => {
    //   if(res.code ==='0'){
    //     this.user=res.data
    //   }
    // })
  }
}
</script>

<style scoped>

</style>