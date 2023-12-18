import React from 'react';
import PageLayout from './PageLayout';
import { Box, Typography, Button, Stack } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from '../image/homeImage.jpg';

const Homepage: React.FC = () => {
    return (
        <PageLayout>
            <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                <Stack gap={4} sx={{ display: 'flex', textAlign: 'center', alignItems: 'center', width: '70%' }}>
                    <Box>
                        <Typography variant="h3">Welcome to Eldercare Connect</Typography>
                        <Typography variant="subtitle1">Empowering Elderly Care, One Connection at a Time</Typography>
                    </Box>
                    <Box component="img" src={logo} sx={{ display: 'flex', justifyContent: 'center', width: '75%' }} />

                    <Box>
                        <Typography variant="body1">Connecting Distributors to Enhance Elderly Lives</Typography>
                    </Box>

                    <Box>
                        <Typography variant="h5">Discover Tailored Care Packages</Typography>
                        <Typography variant="body1">
                            At Eldercare Connect, we understand the importance of personalized care for seniors. Our
                            platform bridges the gap between distributors and elders, ensuring a seamless experience in
                            finding the perfect care packages.
                        </Typography>
                    </Box>

                    <Box>
                        <Typography variant="h5">Effortless Search</Typography>
                        <Typography variant="body1">
                            Search by Age, Group Code, or Radius to find the most suitable care packages for the elderly
                            in your network.
                        </Typography>
                    </Box>

                    <Box>
                        <Typography variant="h5">Comprehensive Listing Search</Typography>
                        <Typography variant="body1">
                            Looking for something specific? Use our advanced search feature to explore a wide range of
                            elder care packages.
                        </Typography>
                    </Box>

                    <Box>
                        <Typography variant="h5">Why Choose Eldercare Connect?</Typography>
                        <Typography variant="body1">
                            Connect with trusted distributors, ensure data security, and enjoy a user-friendly interface
                            for a seamless experience.
                        </Typography>
                    </Box>

                    <Stack direction="row" gap={2}>
                        <Button variant="contained" color="primary" component={Link} to="/client/create">
                            Sign up as a Distributor
                        </Button>
                        <Button variant="outlined" color="primary" component={Link} to="/listing/search/filter">
                            Search Listings
                        </Button>
                    </Stack>
                    <Stack sx={{ textAlign: 'center', my: 3, alignItems: 'center' }}>
                        <Typography variant="h4" paragraph>
                            Distributors: Add Your Facility and Listing
                        </Typography>
                        <Typography variant="body1" paragraph>
                            Join us in providing exceptional care to the elderly. Add your facility and listing to our
                            platform.
                        </Typography>
                        <Stack direction="row" gap={2}>
                            <Button variant="contained" color="primary" component={Link} to="/facility/create">
                                Add a Facility
                            </Button>
                            <Button variant="contained" color="primary" component={Link} to="/listing/create">
                                Add a Listing
                            </Button>
                        </Stack>
                    </Stack>
                </Stack>
            </Box>
        </PageLayout>
    );
};

export default Homepage;
