<template>
  login success
  <el-button type="danger" @click="onClick">退出登录</el-button>
  <el-button type="warning" @click="test">权限测试</el-button>
</template>

<script setup>

import axios from "axios";
import {useRouter} from "vue-router/dist/vue-router";
const router = useRouter()

function onClick(){
  axios({
    method: 'post',
    url: 'http://localhost:8081/out'
  }).then(function (resp){
    router.push('/')
  })

}

function test(){
  axios({
    method: 'get',
    url: 'http://localhost:8081/test'
  }).then(function (resp){
    console.log(resp.data.data + "---------")
    router.push("/Demo")
  }).catch(function (error){
    console.log(error.response.status + "++++++++++++")
  })

}

</script>

<style scoped>

</style>
