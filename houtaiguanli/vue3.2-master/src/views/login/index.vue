<template>
  <div class="login-container">
    <!-- :rules="rules"使用表单校验规则 -->
    <el-form ref="formRef" :model="form" class="login-form" :rules="rules">
      <div class="title-container">
        <h3 class="title">{{ $t('login.title') }}</h3>
      </div>
      <!-- prop="username"绑定校验规则 -->
      <el-form-item prop="account">
        <svg-icon class="svg-container" icon="user"> </svg-icon>
        <el-input v-model="form.account" />
      </el-form-item>
      <el-form-item prop="password">
        <svg-icon class="svg-container" icon="password"> </svg-icon>
        <!-- :type="passwordType"实现密码为暗文，:type动态绑定 -->
        <el-input v-model="form.password" :type="passwordType" />
        <!-- @click="changeType"点击小图标切换为明文，:icon也记得动态绑定 -->
        <svg-icon
          :icon="passwordType === 'password' ? 'eye' : 'eye-open'"
          @click="changeType"
        ></svg-icon>
      </el-form-item>
      <el-button type="primary" class="login-button" @click="handleLogin">{{
        $t('login.btnTitle')
      }}</el-button>
    </el-form>
  </div>
</template>

<script setup>
// 注释掉，直接把数据放在在store/modules/app.js中的actions中
// import { login } from '@/api/login'
import { ref } from 'vue'
// import { Edit } from '@element-plus/icons-vue'
// 使用vuex在登录的时候触发actions
import { useStore } from 'vuex'
// 执行useStore
const store = useStore()
const form = ref({
  // 名字取值要跟接口一致
  account: 'root',
  password: '1234'
})

const rules = ref({
  account: [
    {
      // 必选项
      required: true,
      // 提示词
      message: 'Please input Activity username',
      // 触发条件方式
      trigger: 'blur'
    }
  ],
  password: [
    {
      required: true,
      message: 'Please input Activity password',
      trigger: 'blur'
    }
  ]
})

const formRef = ref(null)
// 点击登录按钮实现统一校验
const handleLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      // 注释掉，直接把数据放在在store/modules/app.js中的actions中
      // await简化操作，与async一起用
      // const msg = await login(form.value)
      // console.log(msg)
      // 调取store里app.js的login方法
      store.dispatch('app/login', form.value)
    } else {
      console.log('error submit!!')
      return false
    }
  })
}

// 实现密码的明暗文切换
// ref('password')因为要切换密码的，不能被写死
const passwordType = ref('password')
const changeType = () => {
  if (passwordType.value === 'password') {
    passwordType.value = 'text'
  } else {
    passwordType.value = 'password'
  }
}
</script>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
$cursor: #fff;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;

    :deep(.el-form-item) {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }

    :deep(.el-input) {
      display: inline-block;
      height: 47px;
      width: 85%;

      .el-input__wrapper {
        background: transparent;
        box-shadow: 0 0 0 0;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;
        input {
          color: $light_gray;
        }
      }
    }
    .login-button {
      width: 100%;
      box-sizing: border-box;
    }
  }

  .tips {
    font-size: 16px;
    line-height: 28px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    ::v-deep .lang-select {
      position: absolute;
      top: 4px;
      right: 0;
      background-color: white;
      font-size: 22px;
      padding: 4px;
      border-radius: 4px;
      cursor: pointer;
    }
  }

  .show-pwd {
    // position: absolute;
    // right: 10px;
    // top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
