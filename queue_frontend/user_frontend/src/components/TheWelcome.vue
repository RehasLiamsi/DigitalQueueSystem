<script setup>
import {onMounted, onUnmounted, ref} from "vue";
import {useRouter} from "vue-router";
import axiosInstance from "@/axiosConfig.js";

const person = ref('');
const router = useRouter();
const positionInQueue = ref('');
const activeQueueId = ref('');

const fetchActiveQueueId = async () => {
  try {
    const response = await axiosInstance.get(`/queue/active/id`)
    activeQueueId.value = response.data;
  } catch (error) {
    await router.push('/noActiveQueue');
  }
}

const addPersonToQueue = async () => {
    const response = await axiosInstance.post(`/person/add/${activeQueueId.value}`);
    person.value = response.data;
    sessionStorage.setItem('person', JSON.stringify(response.data))
    sessionStorage.setItem('joinedQueue', 'true');
    await fetchQueuePosition();
}

const fetchQueuePosition = async () => {
    const response = await axiosInstance.get(`/person/${person.value.personId}/position`);
    positionInQueue.value = response.data;
}

const leaveQueue = async () => {
  const confirmed = window.confirm("Är du säker att du vill hoppa av kön?")
  if (confirmed) {
    try {
      await axiosInstance.put(`/person/left/${person.value.personId}`);
      sessionStorage.removeItem('joinedQueue');
      sessionStorage.removeItem('person');
      person.value = {};
      clearInterval();
      await router.push('/leftQueue')
    } catch (error) {
      window.alert("OOPS! Det gick inte att hoppa av kön")
    }
  }
}

const conditionalAdditionOfPerson = () => {
  const storedPerson = sessionStorage.getItem('person');
  if (storedPerson) {
    person.value = JSON.parse(storedPerson);
    sessionStorage.setItem('joinedQueue', 'true');
    fetchQueuePosition();
  } else {
    addPersonToQueue();
  }
}

const fetchData = async () => {
  await fetchActiveQueueId();
  await conditionalAdditionOfPerson();
}

onMounted(fetchData);
setInterval(fetchQueuePosition, 5000);
onUnmounted(() => {
  clearInterval();
})

</script>

<template>
  <div class="text-box">
    <p>Din plats i kön är: </p>
    <p>{{ positionInQueue }}</p>
  </div>
  <img id="arrow" src="../assets/360_F_543201527_CKqnLxlnnMjYJVLHDk6l69gzQwSfVh8X.jpg" alt="spinning arrow"/>
  <div class="text-box">
  <p>Din väntetid är ungefär</p>
  <p>{{positionInQueue * 5}} min</p>
  </div>
  <button @click="leaveQueue()">Hoppa av kön</button>
</template>

<style scoped>
#arrow{
  border-radius: 70%;
  animation: rotation 10s linear infinite;
}

@keyframes rotation {
  0%, 30%{
    transform: rotate(0deg);
  }
  50%, 100%{
    transform: rotate(360deg);
  }
}
</style>
