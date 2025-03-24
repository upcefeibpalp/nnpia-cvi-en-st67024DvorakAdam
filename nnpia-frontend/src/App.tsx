import './App.css'
import User from "./components/User.tsx"

function App() {

  return (
    <>
        <h1>Users</h1>
        <User id={0} email={"pavel@upce.cz"} active={true} />
    </>
  )
}

export default App
