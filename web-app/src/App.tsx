import { Routes, Route } from 'react-router-dom';
import Homepage from './pages/Homepage';
import Client from './pages/Client/ClientCreate';

const App = () => {
    return (
        <>
            <Routes>
                <Route path="/" element={<Homepage />} />
                <Route path="/client/create" element={<Client />} />
            </Routes>
        </>
    );
};

export default App;
