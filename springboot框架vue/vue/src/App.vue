<template>
  <div>
    <router-view style="width: 100%;height: 800px"/>
  </div>

</template>

<script>
import Header from "@/components/Header.vue";
import Aside from "@/components/Aside"; //你这个地方语法错误 看到没，你刚才写的是@component/Header
import HomeView from '@/views/HomeView.vue'

export default {
  name: "App",
  // components: {
  //   Header,
  //   Aside,
  //   HomeView
  // }
}
</script>