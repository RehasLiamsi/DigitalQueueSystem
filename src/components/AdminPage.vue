<script setup>
import axiosInstance from "../axiosConfig";
import {onMounted, ref} from "vue";
import Cookies from "js-cookie";

const showDropdown = ref(false)
const peopleCount = ref('');
const activeQueue = ref('');
const activeQueueId = ref('');

const fetchEntriesCount = async () => {
  try {
    const response = await axiosInstance.get('/person/count/activeQueue');
    peopleCount.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the entries count:", error);
  }
};

const fetchActiveQueueName = async () => {
  try {
    const response = await axiosInstance.get('/queue/active/name');
    activeQueue.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the active queue:", error);
  }
}

const fetchActiveQueueId = async () => {
  try {
    const response = await axiosInstance.get('queue/active/id')
    activeQueueId.value = response.data;
  } catch (error) {
    console.error("There was an error fetching the active queue Id:", error);
  }
}

const toggleQueueStatus = async () => {
  try {
    await axiosInstance.put(`queue/${activeQueueId.value}`);
  } catch (error) {
    console.error("There was an error toggling the queue status:", error);
  }
}

 const toggleDropdown = () => {
   showDropdown.value = !showDropdown.value
 }

onMounted(fetchEntriesCount);
onMounted(fetchActiveQueueName);
onMounted(fetchActiveQueueId);
</script>

<template>
<div id="banner">
  <p>Welcome Admin!</p>
  <div class="router-links">
  <div class="dropdown" @click="toggleDropdown">
   <span class="dropdown-link">Settings</span>
    <div v-if="showDropdown" class="dropdown-content" >

        <div @click="toggleQueueStatus">Close queue</div>

  </div>
    </div>

<!--      <RouterLink to="/settings">Settings</RouterLink>-->
<!--      <RouterLink to="/statistics">Statistics</RouterLink>-->
      <RouterLink to="/logout">Logout</RouterLink>


</div>
</div>
  <div class="text-box">
    <p v-if="activeQueue===''"> No queue is active.</p>
    <p v-else>The active queue is "<span> {{ activeQueue}}</span>"
    <br><br>There are <span> {{ peopleCount }} </span> people in the queue</p>
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
  flex-direction: column;
  margin: 2em 0;
}

.router-links{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.dropdown-link,
.router-links a{
  text-decoration: none;
  color: white;
  cursor: pointer;
  margin: 10px;
}

.dropdown-link{
  display: inline-flex;
  margin-bottom: 0;
}

.dropdown-content {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  position: absolute;
  background-color: #f9f9f9;
  color: black;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  padding: 12px 0;
  margin-top: 0.5em;
  min-width: 120px;
}

.dropdown-content ul{
  list-style: none;
  margin: 0;
  padding: 0.5em 0 0.5em 1em;
}

.dropdown-content a{
  padding: 0.5em 0.2em;
  width: auto;
  text-decoration: none;
  color: black;

}
</style>