import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { Stack, TextField, Button } from '@mui/material';
import { deleteFacility } from '../../api/FacilityApi';

const FacilityDelete: React.FC = () => {
    const [authentication, setAuthentication] = useState<string>('');
    const [clientId, setClientId] = useState<string | undefined>();
    const [facilityId, setFacilityId] = useState<string | undefined>();

    return (
        <PageLayout title="Facility Delete">
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
                <TextField
                    label="Facility ID"
                    type="string"
                    value={facilityId}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setFacilityId((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <Button
                    variant="contained"
                    disabled={!(clientId && facilityId && authentication)}
                    onClick={async () => {
                        clientId && facilityId && (await deleteFacility(clientId, authentication, facilityId));
                    }}
                >
                    Delete Facility
                </Button>
            </Stack>
        </PageLayout>
    );
};

export default FacilityDelete;
