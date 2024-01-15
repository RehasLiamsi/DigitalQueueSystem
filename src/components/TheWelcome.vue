<script setup>
import {onMounted, onUnmounted, ref} from "vue";
import {useRouter} from "vue-router";
import axiosInstance from "@/axiosConfig.js";

const person = ref('');
const hasJoinedQueue = ref(sessionStorage.getItem('joinedQueue') === 'true');
const router = useRouter();
const positionInQueue = ref('');
const activeQueueId = ref('');
const storedPerson = sessionStorage.getItem('person');

const fetchActiveQueueId = async () => {
  try {
    const response = await axiosInstance.get(`/queue/active/id`)
    activeQueueId.value = response.data;
    console.log('Active queue id is:', activeQueueId.value)
  } catch (error) {
    console.error('Error fetching active queue id:', error);
  }
}
const addPersonToQueue = async () => {
  if (!sessionStorage.getItem('joinedQueue')) {
    try {
      const response = await axiosInstance.post(`/person/add/${activeQueueId.value}`);
      person.value = response.data;
      sessionStorage.setItem('person', JSON.stringify(response.data))
      sessionStorage.setItem('joinedQueue', 'true');
    } catch (error) {
      console.error("Error adding person to queue:", error);
    }
  } else {
    console.log('Already joined the queue', person.value.personId)
    const storedPerson = sessionStorage.getItem('person');
    person.value = JSON.parse(storedPerson);
  }
}

const fetchQueuePosition = async () => {
  try {
    const response = await axiosInstance.get(`/person/${person.value.personId}/position`);
    positionInQueue.value = response.data;
  } catch (error) {
    console.log("Person ID: ", person.value.personId)
    console.error("Error fetching queue position:", error);
  }
}


const leaveQueue = async () => {
  try {
    await axiosInstance.put(`/person/left/${person.value.personId}`);
    sessionStorage.removeItem('joinedQueue');
    sessionStorage.removeItem('person');
    person.value = {};
    clearInterval();
    await router.push('/leftQueue')
  } catch (error) {
    console.error("Error leaving the queue:", error);
  }

}

const conditionalAdditionOfPerson = () => {
  if (storedPerson) {
    console.log('Person already in session storage')
    person.value = JSON.parse(storedPerson);
    sessionStorage.setItem('joinedQueue', 'true');
    fetchQueuePosition();
  } else {
    addPersonToQueue();
  }
}

const fetchData = async () =>{
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
    <div v-if ="hasJoinedQueue">
    <p>Din plats i kön är <span> {{ positionInQueue }}</span></p>
      <button @click="leaveQueue()">Hoppa av kön</button>
    </div>
    <div v-else>
    <p>Tryck på knappen nedan för att ställa dig i kön</p>
      <button @click="conditionalAdditionOfPerson()">Ställ dig i kön</button>
<!--    <p>Din plats i kön är <span> {{ positionInQueue }}</span></p>-->
  </div>
  </div>
  <img src="../assets/360_F_543201527_CKqnLxlnnMjYJVLHDk6l69gzQwSfVh8X.jpg" alt="spinning arrow"/>
</template>
