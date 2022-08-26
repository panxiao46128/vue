import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import '@/assets/global.css'
createApp(App).
  use(store)
    .use(router).
  use(ElementPlus,{size: 'small' }).
  mount('#app')
// ok了 s 这缺文件啊 什么，你不是说项目不行吗，这个问题不是nodejs的问题，是你写的有问题@componet ，都下不了啊，我试试

