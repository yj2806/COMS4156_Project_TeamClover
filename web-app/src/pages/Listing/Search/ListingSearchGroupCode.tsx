import React, { useState } from 'react';
import { Button, TextField, Typography, Stack } from '@mui/material';
import PageLayout from '../../PageLayout';
import { Listing, searchByGroupCode } from '../../../api/ListingApi';

export type ListingGroupSearch = {
    latitude: number;
    longitude: number;
    range: number;
    groupCode: number;
};

const ListingSearchGroupCode: React.FC = () => {
    const [userInput, setUserInput] = useState<ListingGroupSearch>({
        latitude: 0,
        longitude: 0,
        range: 0,
        groupCode: 0,
    });

    const [data, setData] = useState<Listing[]>([]);

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
        field: keyof ListingGroupSearch,
    ) => {
        setUserInput({
            ...userInput,
            [field]: Number(e.target.value),
        });
    };

    const handleSearchClick = async () => {
        const response = await searchByGroupCode(userInput);
        response && setData(response);
    };

    return (
        <PageLayout>
            <Typography variant="h4" gutterBottom>
                Search Listings
            </Typography>
            <TextField
                label="Latitude"
                type="number"
                value={userInput.latitude}
                onChange={(e) => handleInputChange(e, 'latitude')}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Longitude"
                type="number"
                value={userInput.longitude}
                onChange={(e) => handleInputChange(e, 'longitude')}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Range"
                type="number"
                value={userInput.range}
                onChange={(e) => handleInputChange(e, 'range')}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Group Code"
                type="number"
                value={userInput.groupCode}
                onChange={(e) => handleInputChange(e, 'groupCode')}
                fullWidth
                margin="normal"
            />
            <Button variant="contained" color="primary" onClick={handleSearchClick}>
                Search
            </Button>
            {data.map((listing) => (
                <Stack gap={2}>
                    <Typography variant="h4">Listing Information</Typography>
                    <Typography variant="body1">Listing ID: {listing.listingID}</Typography>
                    <Typography variant="body1">Group Code: {listing.groupCode}</Typography>
                    <Typography variant="body1">Item List: {listing.itemList}</Typography>
                    <Typography variant="body1">Age Requirement: {listing.ageRequirement}</Typography>
                    <Typography variant="body1">Veteran Status: {listing.veteranStatus ? 'Yes' : 'No'}</Typography>
                    <Typography variant="body1">Gender: {listing.gender}</Typography>
                    <Typography variant="body1">Public: {listing.public ? 'Yes' : 'No'}</Typography>

                    {/* Display associated facility information */}
                    <Typography variant="h5">Associated Facility</Typography>
                    <Typography variant="body1">Facility ID: {listing.associatedFacility.facilityID}</Typography>
                    <Typography variant="body1">Latitude: {listing.associatedFacility.latitude}</Typography>
                    <Typography variant="body1">Longitude: {listing.associatedFacility.longitude}</Typography>
                    <Typography variant="body1">Email: {listing.associatedFacility.email}</Typography>
                    <Typography variant="body1">Phone: {listing.associatedFacility.phone}</Typography>
                    <Typography variant="body1">Hours: {listing.associatedFacility.hours}</Typography>
                    <Typography variant="body1">Public: {listing.associatedFacility.public ? 'Yes' : 'No'}</Typography>
                </Stack>
            ))}
        </PageLayout>
    );
};

export default ListingSearchGroupCode;
