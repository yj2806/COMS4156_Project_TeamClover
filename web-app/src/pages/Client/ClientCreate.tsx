import { Stack, TextField, FormControl, InputLabel, Select, MenuItem, Button, SelectChangeEvent } from '@mui/material';
import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { createClient } from '../../api/ClientApi';

const ClientCreate: React.FC = () => {
    const [clientType, setClientType] = useState<string>('DISTRIBUTOR');
    const [authentication, setAuthentication] = useState<string>('');
    const handleChange = (event: SelectChangeEvent) => {
        setClientType(event.target.value as string);
    };

    return (
        <PageLayout>
            <Stack gap={2} width={500} p={2}>
                <TextField
                    label="Authentication"
                    type="string"
                    value={authentication}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setAuthentication((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <FormControl fullWidth>
                    <InputLabel id="client-type">Type</InputLabel>
                    <Select labelId="client-type" value={clientType} label="Age" onChange={handleChange}>
                        <MenuItem value={'DISTRIBUTOR'}>DISTRIBUTOR</MenuItem>
                        <MenuItem value={'NON_DISTRIBUTOR'}>NON DISTRIBUTOR</MenuItem>
                    </Select>
                </FormControl>
                <Button
                    disabled={!authentication}
                    onClick={async () => {
                        await createClient({ authentication: authentication || '', type: clientType });
                    }}
                    variant="contained"
                >
                    Create Client
                </Button>
            </Stack>
        </PageLayout>
    );
};

export default ClientCreate;
