<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import Cookies from 'js-cookie';
import axiosInstance from '../axiosConfig';

const router = useRouter();
const email = ref('');
const password = ref('');

const storeToken = (token) => {
  Cookies.set('jwtToken', token, {expires: 1});
}

const login = async (credentials) => {
  try {
    const response = await axiosInstance.post('/api/v1/auth/authenticate', credentials);
    await router.push('/admin');
    const token = response.data.token;
    return token;
  } catch (error) {
    alert('Login failed!');
    throw error;
  }
};

const handleLogin = async () => {
  const credentials = { email: email.value, password: password.value };
  try {
    const token = await login(credentials);
    storeToken(token);
  } catch (error) {
    alert("Error logging in!")
  }
};
</script>


<template>
  <div class="container">
    <div class="login-container">
      <img src="../assets/Untitled.png" alt="login image">
      <form @submit.prevent="handleLogin">
        <div>
          <input type="text" id="email" v-model="email" required placeholder="Email ID">
        </div>
        <div>
          <input type="password" id="password" v-model="password" required placeholder="Password">
        </div>
        <button type="submit">Log in</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  justify-content: space-around;
  align-items: center;
}

.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

img {
  width: 5em;
  margin: 2em;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
  align-content: center;
  flex-wrap: wrap;
}

input {
  border: 3px solid #d0d0d0;
  border-radius: 0.3em;
  width: 30em;
  margin: 1em 0.3em;
  padding: 0.4em;
}

button {
  background: #0167b2;
  color: white;
  border-radius: 0.3em;
  border-style: none;
  padding: 0.5em 5em;
  margin: 1em 0.3em;
  width: 20em;
}
</style>
