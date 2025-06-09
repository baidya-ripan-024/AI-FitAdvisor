import { Box, Button, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import {
  BrowserRouter as Router,
  Navigate,
  Route,
  Routes,
  useLocation,
} from "react-router-dom";
import { setCredentials } from "./store/authSlice";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetail from "./components/ActivityDetail";

const ActivitiesPage = () => {
  return (
    <Box sx={{ p: 2, border: "1px dashed grey" }}>
      <ActivityForm onActivitiesAdded={() => window.location.reload()} />
      <ActivityList />
    </Box>
  );
};

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } =
    useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>
      {!token ? (
        <Box
          sx={{
            height: "100vh",
            width: "100%",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
            background: "linear-gradient(to right, #e3f2fd, #fff1f0)",
            padding: 2,
          }}
        >
          <Box
            sx={{
              backgroundColor: "#ffffff",
              borderRadius: "20px",
              padding: { xs: 4, md: 6 },
              boxShadow: "0px 20px 40px rgba(0, 0, 0, 0.1)",
              maxWidth: 480,
              width: "90%",
              transition: "all 0.3s ease-in-out",
              "&:hover": {
                transform: "scale(1.015)",
                boxShadow: "0px 24px 48px rgba(0, 0, 0, 0.15)",
              },
            }}
          >
            <Typography
              variant="h4"
              gutterBottom
              sx={{
                fontWeight: 700,
                fontFamily: "'Poppins', sans-serif",
                color: "#0d47a1",
              }}
            >
              Welcome to AI-FitAdvisor
            </Typography>

            <Typography
              variant="body1"
              sx={{
                color: "#546e7a",
                mb: 2,
                fontSize: "1.05rem",
              }}
            >
              Your smart personal fitness assistant powered by AI.
            </Typography>

            <Typography
              variant="subtitle1"
              sx={{
                color: "#607d8b",
                mb: 4,
                fontStyle: "italic",
              }}
            >
              Track activities, receive personalized recommendations, and reach
              your goals faster.
              <br /> Please login to continue your journey.
            </Typography>

            <Button
              variant="contained"
              color="primary"
              size="large"
              onClick={() => {
                logIn();
              }}
              sx={{
                px: 5,
                py: 1.7,
                fontSize: "1rem",
                fontWeight: 500,
                borderRadius: "40px",
                textTransform: "capitalize",
                boxShadow: "0 6px 20px rgba(33, 150, 243, 0.3)",
                transition: "all 0.3s ease",
                "&:hover": { backgroundColor: "#1565c0",boxShadow: "0 10px 28px rgba(33, 150, 243, 0.4)", },
              }}
            >
              Login to Get Started
            </Button>
          </Box>
        </Box>
      ) : (
        // <div>
        //   <pre>{JSON.stringify(tokenData, null, 2)}</pre>
        //   <pre>{JSON.stringify(token, null, 2)}</pre>
        // </div>

        <Box sx={{ p: 2, border: "1px dashed grey" }}>
          <Button variant="contained" color="secondary" onClick={logOut}>
            Logout
          </Button>
          <Routes>
            <Route path="/activities" element={<ActivitiesPage />} />
            <Route path="/activities/:id" element={<ActivityDetail />} />

            <Route
              path="/"
              element={
                token ? (
                  <Navigate to="/activities" replace />
                ) : (
                  <div>Welcome! Please Login.</div>
                )
              }
            />
          </Routes>
        </Box>
      )}
    </Router>
  );
}

export default App;
