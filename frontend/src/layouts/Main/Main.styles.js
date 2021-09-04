import { theme } from '../../styles/theme'

export const mainLayoutStyles = {
  height: '100vh',
  background: theme.backgroundColor
}

export const mainLayoutHeaderStyles = {
  background: '#051529',
  display: 'flex',
  alignItems: 'center',
  marginBottom: '20px'
}

export const headerLogoStyles = {
  fontSize: '40px',
  marginRight: '10px',
  color: '#FFFFFF'
}

export const usernameHeaderStyles = {
  marginLeft: 'auto',
  fontSize: '1rem',
  marginRight: '20px',
  display: 'flex',
  alignItems: 'center',
  gap: '5px',
  color: '#FFFFFF'
}

export const headerTitleStyles = {
  fontSize: '25px',
  color: '#FFFFFF'
}

export const mainLayoutContentStyles = {
  background: theme.backgroundColor2,
  height: 'calc(100% - 84px)',
  overflow: 'hidden'
}

export const containerStyles = {
  margin: '0 auto',
  maxWidth: '100%',
  height: '100%',
}

export const linkLogoStyles = {
  display: 'flex',
  alignItems: 'center',
  color: 'rgba(0, 0, 0, 0.85)',
  '&:hover': {
    color: 'rgba(0, 0, 0, 0.85)'
  }
}
