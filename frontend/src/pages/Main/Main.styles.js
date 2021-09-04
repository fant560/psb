import { scrollStyle } from '../../styles/scroll'
import { theme } from '../../styles/theme'

export const mainPageStyle = {
  height: '100%',
  minHeight: '100vh'
}

export const mainPageSiderStyle = {
  background: theme.backgroundColor2,
  padding: '20px 0px',
  height: '100vh',

  '.ant-layout-sider-children': {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center'
  }
}

export const searchStyle = {
  width: '80%',
  marginTop: '0px'
}

export const buttonStyle = {
  height: '50px',
  width: '80%'
}

export const contentStyle = {
  overflow: 'auto',
  height: '100vh',
  ...scrollStyle
}
