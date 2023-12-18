import {
    AppBar,
    Box,
    CssBaseline,
    Divider,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Typography,
} from '@mui/material';
import React from 'react';

type Props = {
    children?: React.ReactNode;
    title?: string;
};

type MenuListing = {
    name: string;
    href: string;
};
const PageLayout: React.FC<Props> = ({ title, children }) => {
    const drawerWidth = 240;
    const clientMenuItems: MenuListing[] = [
        { name: 'Client Create', href: '/client/create' },
        { name: 'Client Delete', href: '/client/delete' },
    ];
    const listingMenuItems: MenuListing[] = [
        { name: 'Listing Create', href: '/listing/create' },
        { name: 'View Listing', href: '/listing/view' },
        { name: 'Edit Listing', href: '/listing/edit' },
        { name: 'Delete Listing', href: '/listing/delete' },
        { name: 'Search Listing by Group Code', href: '/listing/search/group_code' },
        { name: 'Search Listing by Filter', href: '/listing/search/filter' },
    ];

    const facilityMenuItems: MenuListing[] = [
        { name: 'Facility Create', href: '/facility/create' },
        { name: 'Edit Facility', href: '/facility/edit' },
        { name: 'View Facility', href: '/facility/view' },
        { name: 'Delete Facility', href: '/facility/delete' },
    ];

    return (
        <CssBaseline>
            <Box sx={{ display: 'flex' }}>
                <AppBar position="fixed" sx={{ height: '70px', width: `calc(100% - ${drawerWidth}px)` }}>
                    <Typography variant="h5" sx={{ p: 2 }}>
                        Eldercare Connect
                    </Typography>
                </AppBar>
                <Drawer
                    sx={{
                        width: drawerWidth,
                        flexShrink: 0,
                        '& .MuiDrawer-paper': {
                            width: drawerWidth,
                            boxSizing: 'border-box',
                        },
                    }}
                    variant="permanent"
                    anchor="left"
                >
                    <List>
                        <ListItemButton href={'/'} sx={{ p: 0, m: 0 }}>
                            <ListItem sx={{ fontWeight: 'bold' }}>Homepage</ListItem>
                        </ListItemButton>
                    </List>
                    <List>
                        <ListItem sx={{ fontWeight: 'bold' }}>Client</ListItem>
                        {clientMenuItems.map((item) => (
                            <ListItem key={item.name} disablePadding>
                                <ListItemButton href={item.href}>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={item.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider />
                    <List>
                        <ListItem sx={{ fontWeight: 'bold' }}>Facility</ListItem>
                        {facilityMenuItems.map((item) => (
                            <ListItem key={item.name} disablePadding>
                                <ListItemButton href={item.href}>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={item.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider />
                    <List>
                        <ListItem sx={{ fontWeight: 'bold' }}>Listing</ListItem>
                        {listingMenuItems.map((item) => (
                            <ListItem key={item.name} disablePadding>
                                <ListItemButton href={item.href}>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={item.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </Drawer>
                <Box sx={{ mt: '70px', p: 2 }}>
                    {title && <Typography sx={{ fontSize: '24px', fontWeight: 'bold', pb: 3 }}>{title}</Typography>}
                    {children}
                </Box>
            </Box>
        </CssBaseline>
    );
};

export default PageLayout;
