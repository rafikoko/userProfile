// src/App.js
import React, { useEffect, useState } from 'react';

const API_BASE_URL = 'http://localhost:8080/profiles'; // Adjust URL for production

function App() {
  const [profiles, setProfiles] = useState([]);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: ''
  });

  useEffect(() => {
    fetchProfiles();
  }, []);

  const fetchProfiles = async () => {
    const response = await fetch(API_BASE_URL);
    const data = await response.json();
    setProfiles(data);
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch(API_BASE_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    });
    if (response.ok) {
      setFormData({ firstName: '', lastName: '', email: '', phone: '' });
      fetchProfiles();
    } else {
      alert('Error creating profile');
    }
  };

  return (
    <div>
      <h1>User Profiles</h1>
      <form onSubmit={handleSubmit}>
        <input name="firstName" value={formData.firstName} onChange={handleChange} placeholder="First Name" required />
        <input name="lastName" value={formData.lastName} onChange={handleChange} placeholder="Last Name" required />
        <input name="email" value={formData.email} onChange={handleChange} placeholder="Email" type="email" required />
        <input name="phone" value={formData.phone} onChange={handleChange} placeholder="Phone" />
        <button type="submit">Add Profile</button>
      </form>

      <ul>
        {profiles.map(profile => (
          <li key={profile.id}>
            {profile.firstName} {profile.lastName} - {profile.email}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
