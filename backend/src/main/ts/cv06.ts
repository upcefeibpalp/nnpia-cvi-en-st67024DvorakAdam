let jedi1 : string = "Anakin Skywalker";
const jedi2 : string = "Obi-Wan Kenobi";

let numberOfJedis : number | string = 1;
numberOfJedis = "252";
numberOfJedis = 266;

if(typeof numberOfJedis === "number"){
    console.log(numberOfJedis);
}else{
    console.log(JSON.parse(numberOfJedis));
}

function isSkywalker2(jedi:string):boolean{
    return jedi.includes("Skywalker");
}

console.log(isSkywalker2("Luke Skywalker") === true);
console.log(jedi1);
console.log(jedi2);

interface Lightsaber{
    color: string
}

interface Jedi{
    name : string,
    age: number,
    sword?: Lightsaber | null
}


const jediJson = {
    name : "Luke Skywalker",
    age: 35,
    sword:{
        color: "blue"
    }
}

function isSkywalker(jedi: Jedi):boolean{
    return jedi.name.includes("Skywalker");
}

console.log(isSkywalker(jediJson) === true);