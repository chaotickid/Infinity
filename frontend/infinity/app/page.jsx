import Login from '@/pages/Login'
import Image from 'next/image'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function Home() {

  return (
    <div>
      <Login></Login>
      <ToastContainer />
    </div>
  )
}
