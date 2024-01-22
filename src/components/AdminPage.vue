<script setup>
import "bootstrap/dist/css/bootstrap.min.css";
import axiosInstance from "../axiosConfig";
import {onMounted, ref} from "vue";
import BannerComponent from "@/components/BannerComponent.vue";

const peopleCount = ref('');
const activeQueueName = ref('');
const activeQueueId = ref('');
const totalPeopleServed = ref('');
const firstPersonInQueue = ref('');
const buttonText = ref('Close Queue');

const fetchPeopleCount = async () => {
  if (activeQueueId.value) {
    const response = await axiosInstance.get('/person/count/activeQueue');
    peopleCount.value = response.data;
  }
};

const fetchActiveQueueName = async () => {
  const response = await axiosInstance.get('/queue/active/name');
  activeQueueName.value = response.data;
}

const fetchActiveQueueId = async () => {
  const response = await axiosInstance.get('/queue/active/id');
  activeQueueId.value = response.data;
}

const fetchTotalPeopleServedToday = async () => {
  const response = await axiosInstance.get(`/person/count/all/${activeQueueId.value}`);
  totalPeopleServed.value = response.data;
}

const fetchFirstPersonInQueue = async () => {
  const response = await axiosInstance.get(`/person/activeQueue/first`);
  firstPersonInQueue.value = response.data;
}

const dropFirstPersonInQueue = async () => {
  await fetchFirstPersonInQueue();
  try {
    await axiosInstance.put(`person/left/${firstPersonInQueue.value.personId}`)
  } catch (error) {
    alert("OOPS! Couldn't go to next person in queue!")
    console.error(error)
  }
}

const closeQueue = async () => {
  buttonText.value = buttonText.value === 'Close Queue' ? 'Open Queue' : 'Close Queue'
  try {
    await axiosInstance.put(`queue/${activeQueueId.value}`);
  } catch (error) {
    alert("OOPS! Couldn't close queue!")
  }
}

const fetchDataOnMounted = async () => {
  await fetchActiveQueueName();
  await fetchAllData();
}

const fetchAllData = async () => {
  await fetchActiveQueueId();
  if (activeQueueId.value) {
    await fetchPeopleCount();
  }
  await fetchTotalPeopleServedToday();
}

onMounted(fetchDataOnMounted);
setInterval(fetchAllData, 3000);
</script>

<template>
  <BannerComponent/>

  <div class="text-box">
    <div v-if="activeQueueName===''">
      <p> No queue is active.</p>
    </div>
    <div v-else>
      <p>The active queue is "<span> {{ activeQueueName }}</span>"
        <br><br>There are <span> {{ peopleCount }} </span> people standing in this queue.
        <br><br>Total number of people served today is : <span> {{ totalPeopleServed }}</span></p>
      <button type="button" @click="dropFirstPersonInQueue()">
        Go to next person in Queue
      </button>
      <br>
      <button type="button" @click="closeQueue()">
        {{ buttonText }}
      </button>
    </div>
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