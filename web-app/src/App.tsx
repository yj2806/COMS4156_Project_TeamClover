import { Routes, Route } from 'react-router-dom';
import Homepage from './pages/Homepage';
import ClientCreate from './pages/Client/ClientCreate';
import CreateFacility from './pages/Facility/CreateFacility';
import CreateListing from './pages/Listing/CreateListing';
import ListingView from './pages/Listing/ListingView';
import ListingEdit from './pages/Listing/ListingEdit';
import FacilityEdit from './pages/Facility/FacilityEdit';
import FacilityView from './pages/Facility/FacilityView';
import FacilityDelete from './pages/Facility/FacilityDelete';
import ClientDelete from './pages/Client/ClientDelete';
import ListingSearchGroupCode from './pages/Listing/Search/ListingSearchGroupCode';
import ListingSearchWithFilter from './pages/Listing/Search/ListingSearchWithFilter';
import ListingDelete from './pages/Listing/ListingDelete';

const App = () => {
    return (
        <>
            <Routes>
                <Route path="/" element={<Homepage />} />
                <Route path="/client/">
                    <Route path="create" element={<ClientCreate />} />
                    <Route path="delete" element={<ClientDelete />} />
                </Route>
                <Route path="/facility/">
                    <Route path="create" element={<CreateFacility />} />
                    <Route path="edit" element={<FacilityEdit />} />
                    <Route path="view" element={<FacilityView />} />
                    <Route path="delete" element={<FacilityDelete />} />
                </Route>
                <Route path="/listing/">
                    <Route path="create" element={<CreateListing />} />
                    <Route path="view" element={<ListingView />} />
                    <Route path="edit" element={<ListingEdit />} />
                    <Route path="delete" element={<ListingDelete />} />
                    <Route path="search/">
                        <Route path="group_code" element={<ListingSearchGroupCode />} />
                        <Route path="filter" element={<ListingSearchWithFilter />} />
                    </Route>
                </Route>
            </Routes>
        </>
    );
};

export default App;
