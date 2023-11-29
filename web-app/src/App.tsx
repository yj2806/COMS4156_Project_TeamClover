import { Routes, Route } from 'react-router-dom';
import Homepage from './pages/Homepage';
import ClientCreate from './pages/Client/ClientCreate';
import CreateFacility from './pages/Facility/CreateFacility';
import CreateListing from './pages/Listing/CreateListing';

const App = () => {
    return (
        <>
            <Routes>
                <Route path="/" element={<Homepage />} />
                <Route path="/client/create" element={<ClientCreate />} />
                <Route path="/facility/create" element={<CreateFacility />} />
                <Route path="/listing/" element={<CreateListing />}>
                    <Route path="create" element={<CreateListing />} />
                </Route>
            </Routes>
        </>
    );
};

export default App;
