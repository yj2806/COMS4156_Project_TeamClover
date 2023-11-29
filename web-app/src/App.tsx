import { Routes, Route } from 'react-router-dom';
import Homepage from './pages/Homepage';
import ClientCreate from './pages/Client/ClientCreate';

const App = () => {
    return (
        <>
            <Routes>
                <Route path="/" element={<Homepage />} />
                <Route path="/client/create" element={<ClientCreate />} />
            </Routes>
        </>
    );
};

export default App;
