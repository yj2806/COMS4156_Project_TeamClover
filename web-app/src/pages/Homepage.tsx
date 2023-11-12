import React from 'react';
import { AppBar, CssBaseline, Typography } from '@mui/material';

const Homepage: React.FC = () => {
    return (
        <CssBaseline>
            <AppBar position="sticky">
                <Typography variant="h5" sx={{ p: 2 }}>
                    Team Clove - Front End
                </Typography>
            </AppBar>
        </CssBaseline>
    );
};

export default Homepage;
