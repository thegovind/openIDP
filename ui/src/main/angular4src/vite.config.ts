import { defineConfig } from 'vite';

export default defineConfig({
  build: {
    target: 'es2020',
    outDir: 'dist',
    emptyOutDir: true,
    rollupOptions: {
      input: 'src/index.html'
    }
  },
  optimizeDeps: {
    include: ['@angular/common', '@angular/forms', '@angular/router', 'zone.js']
  },
  server: {
    port: 4200,
    host: true
  },
  define: {
    'process.env': {},
    'global': 'globalThis'
  },
  esbuild: {
    target: 'es2020'
  }
});
