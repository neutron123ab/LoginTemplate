<template>
  <el-form
      label-position="top"
      label-width="100px"
      :model="formLabelAlign"
      style="max-width: 460px"
  >
    <el-form-item label="用户名">
      <el-input v-model="formLabelAlign.username" />
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="formLabelAlign.password" />
    </el-form-item>
    <el-form-item label="确认密码" v-if="show">
      <el-input v-model="formLabelAlign.confirmPwd" />
    </el-form-item>
    <el-button type="primary" @click="onClick" v-if="show === false">登录</el-button>
    <el-button type="primary" @click="onClickBack" v-if="show === true">返回</el-button>
    <el-button type="success" @click="onClickSignUp">注册</el-button>
  </el-form>
</template>

<script setup>

import {reactive, ref} from "vue";
import axios from "axios";

import {useRouter} from "vue-router/dist/vue-router";
import {JSEncrypt} from "jsencrypt";
import store from "../store";

const show = ref(false)
const Authorization = ref('')
const formLabelAlign = reactive({
  username: '',
  password: '',
  confirmPwd: ''
})

const router = useRouter();

function onClick(){
  axios({
    method: 'post',
    url: "http://localhost:8081/login",
    data: {
      ...formLabelAlign
    },
    headers: {
      'Access-Control-Allow-Origin': '*',
    }
  }).then(function (resp){
    console.log(resp.data)
    if(resp.data !== null){
      store.commit('setAuthorization', resp.data.data)
      router.push('/index')
    }
  })
}
//注册
function onClickSignUp(){
  if(show.value === true){
    //获取rsa公钥
    let publicKey;
    axios({
      method: 'get',
      url: 'http://localhost:8081/getPublicKey',
    }).then(function (resp){
      publicKey = resp.data.data
      //rsa加密
      let encrypt = new JSEncrypt();
      encrypt.setPublicKey(publicKey);
      let encodePassword = encrypt.encrypt(formLabelAlign.password);

      axios({
        method: 'post',
        url: 'http://localhost:8081/login/signUp',
        data: {
          username: formLabelAlign.username,
          password: encodePassword
        }
      }).then(function (resp){
        console.log(resp.data.data)
      })
    })




    show.value = false;
  } else {
    show.value = true;
  }
}

function onClickBack(){
  show.value = false;
}

</script>

<style scoped>

</style>
