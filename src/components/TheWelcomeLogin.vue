<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();
const email = ref('');
const password = ref('');

const login = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/v1/auth/authenticate', {
      email: email.value,
      password: password.value
    });
    console.log(response.data);
    alert('Login successful!');
    store.dispatch('login', response.data.user);
    router.push('/');
  } catch (error) {
    console.error(error.response.data);
    alert('Login failed!');
  }
}
</script>

<template>
  <div class="container">
    <div class="login-container">
      <img src="../assets/Untitled.png" alt="login image">
      <form @submit.prevent="login">
        <div>
          <!--          <label for="email">Username</label>-->
          <input type="text" id="email" v-model="email" required placeholder="Email ID">
        </div>
        <div>
          <!--          <label for="password">Password</label>-->
          <input type="password" id="password" v-model="password" required placeholder="Password">
        </div>
        <button type="submit">Log in</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container{
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  justify-content: space-around;
  align-items: center;
}
.login-container{
  display: flex;
  flex-direction: column;
  align-items: center;
}
img {
  width: 5em;
  margin: 2em;
}
form{
  display: flex;
  flex-direction: column;
  align-items: center;
  align-content: center;
  flex-wrap: wrap;
}
input{
  border:3px solid #d0d0d0;
  border-radius: 0.3em;
  width: 30em;
  margin:1em 0.3em;
  padding: 0.4em;
}
button{
  background: #0167b2;
  color: white;
  border-radius: 0.3em;
  border-style: none;
  padding: 0.5em 5em;
  margin:1em 0.3em;
  width: 20em;
}
</style>
