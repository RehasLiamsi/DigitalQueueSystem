<script setup>
import axiosInstance from "../axiosConfig";
import {onMounted, ref} from "vue";
import Cookies from "js-cookie";

// const showDropdown = ref(false)
const peopleCount = ref('');
const activeQueue = ref('');

const fetchEntriesCount = async () => {
  try {
    const response = await axiosInstance.get('/person/count');
    peopleCount.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the entries count:", error);
  }
};

const fetchActiveQueue = async () => {
  try {
    const response = await axiosInstance.get('');
    activeQueue.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the active queue:", error);
  }
}

// const toggleDropdown = () => {
//   showDropdown.value = !showDropdown.value
// }

onMounted(fetchEntriesCount);
onMounted(fetchActiveQueue);
</script>

<template>
<div id="banner">
  <p>Welcome Admin!</p>
<!--  <div class="dropdown" @click="toggleDropdown">-->
<!--    <span class="dropdown-link">Menu</span>-->
<!--    <div v-if="showDropdown" class="dropdown-content" >-->
  <div class="router-links">
      <RouterLink to="/settings">Settings</RouterLink>
      <RouterLink to="/statistics">Statistics</RouterLink>
      <RouterLink to="/logout">Logout</RouterLink>
<!--    </div>-->
<!--  </div>-->
</div>
</div>
  <div class="text-box">
    <p>The active queue is <span> {{ activeQueue}}</span></p>
    <p>There are <span> {{ peopleCount }} </span> people in the queue</p>
  </div>
</template>

<style scoped>
#banner{
  background: #0167b2;
  padding: 0.4em 4em;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.text-box{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}

.text-box p{
  padding: 2em 0;
}

/*.dropdown {*/
/*  position: relative;*/
/*  display: inline-block;*/
/*}*/

.router-links{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
/*.dropdown-link {*/
.router-links a{
  text-decoration: none;
  color: white;
  cursor: pointer;
  margin: 10px;
}

/*.dropdown-content {*/
/*  display: flex;*/
/*  flex-direction: column;*/
/*  position: absolute;*/
/*  background-color: #f9f9f9;*/
/*  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);*/
/*  padding: 12px 16px;*/
/*  left: -50%;*/
/*  margin-top: 0.5em;*/
/*}*/

/*.dropdown-content a{*/
/*  padding: 0.5em 0.2em;*/
/*  width: auto;*/
/*  text-decoration: none;*/
/*  color: black;*/
/*}*/
</style>