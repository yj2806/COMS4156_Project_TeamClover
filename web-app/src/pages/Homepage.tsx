import React, { useState } from 'react';
import {
    AppBar,
    Button,
    CssBaseline,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    Stack,
    TextField,
    Typography,
} from '@mui/material';

const Homepage: React.FC = () => {
    const [clientType, setClientType] = useState<string>('DISTRIBUTOR');
    const handleChange = (event: SelectChangeEvent) => {
        setClientType(event.target.value as string);
    };
    return (
        <CssBaseline>
            <AppBar position="sticky">
                <Typography variant="h5" sx={{ p: 2 }}>
                    Team Clove - Front End
                </Typography>
            </AppBar>
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
        </CssBaseline>
    );
};

export default Homepage;
