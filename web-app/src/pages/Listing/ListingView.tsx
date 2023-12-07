import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { Listing, getAllListing } from '../../api/ListingApi';
import { Button, Stack, TextField, Typography } from '@mui/material';

const ListingView: React.FC = () => {
    const [clientId, setClientId] = useState<string>('');
    const [authentication, setAuthentication] = useState<string>('');

    const [data, setData] = useState<Listing[]>([]);

    return (
        <PageLayout>
            <Stack gap={2}>
                <TextField
                    label="Client ID"
                    type="string"
                    value={clientId}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setClientId((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <TextField
                    label="Client Authentication"
                    type="string"
                    value={authentication}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setAuthentication((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <Button
                    variant="contained"
                    onClick={async () => {
                        const response = await getAllListing(clientId, authentication);
                        response && setData(response);
                    }}
                >
                    Get All Listing
                </Button>
            </Stack>
            {data.map((listing) => (
                <div>
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
                </div>
            ))}
        </PageLayout>
    );
};

export default ListingView;
