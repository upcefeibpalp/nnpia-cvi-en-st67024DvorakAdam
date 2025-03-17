let jedi1 = "Anakin Skywalker";
const jedi2 = "obi-Wan Kenobi";
var jedi3 = "Luke Skywalker";

//var nikdy nepoužívat - má globální rozsah

jedi1 = "Darth Vader";

console.log(`Jedi1: ${jedi1}`);
console.log(jedi2);
console.log(jedi3);

const numberOfSiths = "1";
const numberOfJedis = 1;


//== kontroluje jen hodnotu a lze porovnat string i s číslem
if(numberOfJedis == numberOfSiths){
    console.log("Sily jsou vyrovnane");
}

//používat trojité rovná se --> kontroluje nejen hodnotu ale i datový typ

if(numberOfJedis == numberOfSiths){
    console.log("Sily jsou vyrovnane");
}

//pole:
const jedis2 = Array.of("Yoda", jedi1, jedi2);
const jedis = ["Yoda", jedi1, jedi2];

jedis.forEach(j => console.log(j));

console.log("-------------------");

jedis.map(j => `Jedi: ${j}`)
    .forEach(j => console.log(j));

console.log("-------------------");

jedis.filter(j => !j.includes("Darth"))
    .map(j => `Jedi: ${j}`)
    .forEach(j => console.log(j));

console.log("-------------------");

if(jedis.includes(jedi1)){
    console.log("There is an impostor among us");
}

//do závorek jde deklarovat nové proměnné --> vypisuje to hodnoty z pole podle pořadí
// ... 3 tečky znamená že se do proměnné uloží zbytek pole
const [jedi4, ...restOfJedis] = jedis;
console.log(`Jedi 4 ${jedi4}`);
console.log(`Jedis rest ${restOfJedis}`);

console.log("-------------------");

const jediJson = {
    name : "Luke Skywalker",
    age: 35,
    sword:{
        color: "blue"
    }
}

//jde to i takto:
/*
const jediJson = {
    "name" : "Luke Skywalker",
    "age": 35,
    "sword":{
        "color": "blue"
    }
}
*/

console.log(jediJson);
const jediJsonString = JSON.stringify(jediJson);
console.log(`the best jedi is: ${jediJsonString}`);
//takhle to bude vypisovat ale místo výpisu [object Object] --> proto se používá stringify metoda
console.log(`zpatky do json ze stringu: ${JSON.parse(jediJsonString)}`);

console.log("-------------------");

const {age, name} = jediJson;
console.log(`Age of Jedi JSON: ${age}`);
console.log(`Name of Jedi JSON: ${name}`);
console.log(`Full JSON: ${JSON.stringify(jediJson)}`)

//funguje i klasická tečková notace, ale prej je to trapný :(
console.log(`Trapný způsob: Age of Jedi JSON: ${jediJson.age}`);


//kontrola že proměnná v json není null
if(jediJson.sword && jediJson.sword.color === "blue"){
    console.log("Lightsaber is blue");
}

//úplně stejná kontrola je toto pomocí toho otazníku:
if(jediJson.sword?.color === "blue"){
    console.log("Lightsaber is blue");
}

console.log("-------------------");
//funkce:

function isjediArmed(jedi){
    return jedi.sword !== null; //nutné použít undefined místo null
    //undefined značí že sword není vůbec definovaný
    //null zančí že nemá definovanou žádnou hodnotu
    //ale jako null použít můžu
}

//uložení funkce do proměnné
const isjediArmedArrow = (jedi) => {
    return jedi.sword !== null;
}

console.log(isjediArmed(jediJson));
console.log(isjediArmed(isjediArmedArrow));


console.log("-------------------");

//Asynchronní části kodu:
//lze použít api: https://swapi.dev

/*
//aby se z toho dostaly ty data v nějaké podobě která nění jen promise, tak to musím udělat těmahle sračkama
const restResponse = fetch("https://swapi.dev/api/people/4")
    .then(response => response.json())
    .then(responseJson => console.log(responseJson));
*/

const isMyFather = async (fatherId, jedi) => {
    const response = await fetch(`https://swapi.dev/api/people/${fatherId}`);
    const responseJson = await response.json();

    if(responseJson.name === jedi){
        console.log("Is your father");
    }
    else{
        console.log("Is not your father");
    }
}

await isMyFather()


//TS online konzole: https://www.typescriptlang.org/play

