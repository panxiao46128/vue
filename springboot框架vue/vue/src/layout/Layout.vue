<template>
<div>
<!--头部-->
  <Header/>


  <div style="display: flex;width: 100%;height: 100%">

<!--    侧边栏-->
    <Aside style="width: 15%"/>

    <!--内容区域-->
    <router-view style="width: 85%;height: 100%;"/>
  </div>
</div>
</template>

<script>
import Header from "@/components/Header.vue";
import Aside from "@/components/Aside";
import HomeView from "@/views/HomeView";
export default {
  name: "layout",
  components: {
    Header,
    Aside,
    HomeView
  }
}
</script>

<style scoped>

</style>