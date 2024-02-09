import commonjs from '@rollup/plugin-commonjs';
import copy from 'rollup-plugin-copy';
import css from 'rollup-plugin-css-only'
import resolve from '@rollup/plugin-node-resolve';

export default {
  input: 'dist/src/components/starter/starter.js',
  output: [{ file:'../../src/main/resources/webroot/starter/bundle.js', format: 'es' }],
  plugins: [
    resolve(),
    commonjs(),
    css({
      output: 'bundle.css' 
    }),
    copy({
      copyOnce: true,
      targets: [
        {
          src: 'node_modules/@shoelace-style/shoelace/dist/assets',
          dest: '../../src/main/resources/webroot/starter/shoelace'
        }
      ]
    })
  ]
};