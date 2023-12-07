import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { Button, Divider, Stack, TextField, Typography } from '@mui/material';
import { Facility, getFacilityByClient } from '../../api/FacilityApi';

const FacilityView: React.FC = () => {
    const [clientId, setClientId] = useState<string>('');
    const [authentication, setAuthentication] = useState<string>('');

    const [data, setData] = useState<Facility[]>([]);

    return (
        <PageLayout>
            <Stack gap={3}>
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
                        const response = await getFacilityByClient(clientId, authentication);
                        response && setData(response);
                    }}
                >
                    Get All Facility
                </Button>

                {data.map((facility) => (
                    <>
                        <Divider sx={{ p: 4 }} />
                        <Stack gap={2} p={4}>
                            <Typography variant="h6">Facility ID: {facility.facilityID}</Typography>
                            <Typography>Email: {facility.email}</Typography>
                            <Typography>Hours: {facility.hours}</Typography>
                            <Typography>Latitude: {facility.latitude}</Typography>
                            <Typography>Longitude: {facility.longitude}</Typography>
                            <Typography>Phone: {facility.phone}</Typography>
                            <Typography>Public: {facility.public ? 'Yes' : 'No'}</Typography>
                        </Stack>
                    </>
                ))}
            </Stack>
        </PageLayout>
    );
};

export default FacilityView;
