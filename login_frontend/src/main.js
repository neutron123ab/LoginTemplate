import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import './assets/main.css'
import './interceptor/index.js'

import store from "./store";

const app = createApp(App)

app.use(router)
    .use(store)

app.mount('#app')
