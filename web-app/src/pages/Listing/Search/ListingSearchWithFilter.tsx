import React, { useState } from 'react';
import { Button, TextField, Typography, Checkbox, FormControlLabel, Stack, Divider } from '@mui/material';
import PageLayout from '../../PageLayout';
import { Listing, searchByFilter } from '../../../api/ListingApi';

export type ListingSearchByFilter = {
    latitude: number;
    longitude: number;
    range: number;
    itemContained?: string;
    age?: number;
    veteranStatus?: boolean;
    gender?: string;
};

const ListingSearchWithFilter: React.FC = () => {
    const [userInput, setUserInput] = useState<ListingSearchByFilter>({
        latitude: 0,
        longitude: 0,
        range: 0,
    });

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
        field: keyof ListingSearchByFilter,
    ) => {
        setUserInput({
            ...userInput,
            [field]: e.target.value,
        });
    };

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>, field: keyof ListingSearchByFilter) => {
        setUserInput({
            ...userInput,
            [field]: e.target.checked,
        });
    };

    const [data, setData] = useState<Listing[]>([]);

    const handleSearchClick = async () => {
        const response = await searchByFilter(userInput);
        response && setData(response);
    };

    return (
        <PageLayout title=" Search Listings by Filter">
            <TextField
                required
                label="Latitude"
                type="number"
                value={userInput.latitude}
                onChange={(e) => handleInputChange(e, 'latitude')}
                fullWidth
                margin="normal"
            />
            <TextField
                required
                label="Longitude"
                type="number"
                value={userInput.longitude}
                onChange={(e) => handleInputChange(e, 'longitude')}
                fullWidth
                margin="normal"
            />
            <TextField
                required
                label="Range"
                type="number"
                value={userInput.range}
                onChange={(e) => handleInputChange(e, 'range')}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Item Contained"
                value={userInput.itemContained || ''}
                onChange={(e) => handleInputChange(e, 'itemContained')}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Age"
                type="number"
                value={userInput.age || 0}
                onChange={(e) => handleInputChange(e, 'age')}
                fullWidth
                margin="normal"
            />
            <FormControlLabel
                control={
                    <Checkbox
                        checked={userInput.veteranStatus || false}
                        onChange={(e) => handleCheckboxChange(e, 'veteranStatus')}
                    />
                }
                label="Veteran Status"
            />
            <TextField
                label="Gender"
                value={userInput.gender || ''}
                onChange={(e) => handleInputChange(e, 'gender')}
                fullWidth
                margin="normal"
            />
            <Button variant="contained" color="primary" onClick={handleSearchClick}>
                Search
            </Button>
            {data.map((listing) => (
                <Stack gap={2} pt={3}>
                    <Typography variant="h4">Listing ID: {listing.listingID}</Typography>
                    <Typography variant="body1">Group Code: {listing.groupCode}</Typography>
                    <Typography variant="body1">Item List: {listing.itemList}</Typography>
                    <Typography variant="body1">Age Requirement: {listing.ageRequirement}</Typography>
                    <Typography variant="body1">Veteran Status: {listing.veteranStatus ? 'Yes' : 'No'}</Typography>
                    <Typography variant="body1">Gender: {listing.gender}</Typography>
                    <Typography variant="body1">Public: {listing.public ? 'Yes' : 'No'}</Typography>

                    <Typography variant="h5">Associated Facility</Typography>
                    <Typography variant="body1">Facility ID: {listing.associatedFacility.facilityID}</Typography>
                    <Typography variant="body1">Latitude: {listing.associatedFacility.latitude}</Typography>
                    <Typography variant="body1">Longitude: {listing.associatedFacility.longitude}</Typography>
                    <Typography variant="body1">Email: {listing.associatedFacility.email}</Typography>
                    <Typography variant="body1">Phone: {listing.associatedFacility.phone}</Typography>
                    <Typography variant="body1">Hours: {listing.associatedFacility.hours}</Typography>
                    <Typography variant="body1">Public: {listing.associatedFacility.public ? 'Yes' : 'No'}</Typography>
                    <Divider />
                </Stack>
            ))}
        </PageLayout>
    );
};

export default ListingSearchWithFilter;
