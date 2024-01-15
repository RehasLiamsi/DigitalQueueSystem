<script setup>
import "bootstrap/dist/css/bootstrap.min.css";
import axiosInstance from "../axiosConfig";
import {onMounted, ref} from "vue";
import Cookies from "js-cookie";
import BannerComponent from "@/components/BannerComponent.vue";

const peopleCount = ref('');
const activeQueueName = ref('');
const activeQueueId = ref('');
const totalPeopleServed = ref('');
const firstPersonInQueue = ref('');

const fetchPeopleCount = async () => {
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
    activeQueueName.value = response.data;
  } catch (error) {
    console.log("Stored Token: ", Cookies.get('jwtToken'));
    console.error("There was an error fetching the active queue:", error);
  }
}

const fetchActiveQueueId = async () => {
  try {
    const response = await axiosInstance.get('/queue/active/id');
    activeQueueId.value = response.data;
  } catch (error) {
    console.error("Error fetching active queue ID:", error);
  }
}

const fetchTotalPeopleServedToday = async () => {
  try {
    const response = await axiosInstance.get(`/person/count/all/${activeQueueId.value}`);
    totalPeopleServed.value = response.data;
  } catch (error) {
    console.log("Active queue Id not being fetched: ", activeQueueId.value)
    console.error("Error fetching total people served since start")
  }
}

const fetchFirstPersonInQueue = async () => {
  try {
    const response = await axiosInstance.get(`/person/activeQueue/first`);
    firstPersonInQueue.value = response.data;
  } catch (error) {
    console.error("Error fetching first person in queue:", error)
  }
}

const dropFirstPersonInQueue = async () => {
  try {
    await axiosInstance.put(`person/left/${firstPersonInQueue.value.personId}`)
  } catch (error) {
    console.error("Error dropping first person from queue:", error);
  }
}

const fetchData = async () => {
  await fetchActiveQueueId();
  await fetchActiveQueueName();
  await fetchPeopleCount();
  await fetchTotalPeopleServedToday();
  await fetchFirstPersonInQueue();
}

onMounted(fetchData);
setInterval(fetchData, 3000);
</script>

<template>
  <BannerComponent/>

  <div class="text-box">
    <p v-if="activeQueueName===''"> No queue is active.</p>
    <p v-else>The active queue is "<span> {{ activeQueueName }}</span>"
      <br><br>There are <span> {{ peopleCount }} </span> people standing in this queue.
      <br><br>Total number of people served today is : <span> {{ totalPeopleServed }}</span></p>

    <button type="button" @click="dropFirstPersonInQueue()">
      Go to next person in Queue
    </button>
  </div>
</template>

<style scoped>
.text-box {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
  flex-direction: column;
  margin: 3em 0;
}

button {
  background: #0167b2;
  color: white;
  border-radius: 0.3em;
  border-style: none;
  padding: 0.5em 5em;
  margin: 1em 0.3em;
}
</style>