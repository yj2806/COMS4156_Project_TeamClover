import { Stack, TextField, FormControl, InputLabel, Select, MenuItem, Button, SelectChangeEvent } from '@mui/material';
import React, { useState } from 'react';

const Client: React.FC = () => {
    const [clientType, setClientType] = useState<string>('DISTRIBUTOR');
    const handleChange = (event: SelectChangeEvent) => {
        setClientType(event.target.value as string);
    };
    return (
        <Stack gap={2} width={500} p={2}>
            <TextField label="Authentication" />
            <FormControl fullWidth>
                <InputLabel id="client-type">Type</InputLabel>
                <Select labelId="client-type" value={clientType} label="Age" onChange={handleChange}>
                    <MenuItem value={'DISTRIBUTOR'}>DISTRIBUTOR</MenuItem>
                    <MenuItem value={'NON_DISTRIBUTOR'}>NON DISTRIBUTOR</MenuItem>
                </Select>
            </FormControl>
            <Button>Create Client</Button>
        </Stack>
    );
};

export default Client;
