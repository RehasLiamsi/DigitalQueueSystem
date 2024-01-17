<script setup>
import BannerComponent from "@/components/BannerComponent.vue";
import {ref} from "vue";
import axiosInstance from "@/axiosConfig";

const email = ref('');
const password = ref('');

const addNewAdmin = async (credentials) => {
  try {
    await axiosInstance.post('/api/v1/auth/register', credentials);
    alert("Successfully added new admin!")
  } catch (error) {
    alert("OOPS! Error registering a new admin!")
  }

}

const registerCredentials = async () => {
  const credentials = {email: email.value, password: password.value};
  try {
    await addNewAdmin(credentials)
  } catch (error) {
    alert("Something went wrong with the email or password you entered")
  }
}
</script>
<template>
    <BannerComponent/>
    <form>
      <div>
      <input type="text" id="email" v-model="email" required placeholder="Email ID">
    </div>
    <div>
      <input type="password" id="password" v-model="password" required placeholder="Password">
    </div>
    <button @click="registerCredentials()">Add New Admin</button>
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  align-content: center;
  flex-wrap: wrap;
  margin: 2em 0;
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